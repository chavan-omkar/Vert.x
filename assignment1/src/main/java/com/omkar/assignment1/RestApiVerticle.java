package com.omkar.assignment1;

import com.omkar.assignment1.Verticles.CrudRestApi;
import com.omkar.assignment1.config.ConfigLoader;
import com.omkar.assignment1.config.CrudConfig;
import com.omkar.assignment1.db.DBPools;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.sqlclient.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestApiVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        ConfigLoader.load(vertx)
                .onFailure(startPromise::fail)
                .onSuccess(configuration -> {
                   LOG.info("Reterieved Configuration: {}",configuration);
                   startHttpServerAndAttachRoutes(startPromise, (CrudConfig) configuration);
                });
    }

    private void startHttpServerAndAttachRoutes(final Promise<Void> startPromise,
                                                final CrudConfig configuration) {

        final Pool db = DBPools.createPgPool(configuration,vertx);

        final Router restApi = Router.router(vertx);
        restApi.route()
                .handler(BodyHandler.create())
                .failureHandler(handleFailure());
        CrudRestApi.attach(restApi,db);


        vertx.createHttpServer()
                .requestHandler(restApi)
                .exceptionHandler(error -> LOG.error("Http server error: ",error))
                .listen(configuration.getServerPort(), http ->{
                    if(http.succeeded()){
                        startPromise.complete();
                        LOG.info("Http server started on port {}",configuration.getServerPort());
                    }else {
                        startPromise.fail(http.cause());
                    }
                });
    }

    private Handler<RoutingContext>handleFailure(){
        return  errorContext ->{
            if(errorContext.response().ended()){
                return;
            }
            LOG.error("Route Error:",errorContext.failure());
            errorContext.response()
                    .setStatusCode(500)
                    .end(
                            new JsonObject().put("message","Something went wrong:(").toBuffer()
                    );
        };
    }
}
