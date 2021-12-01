package com.omkar.assignment1;

import com.omkar.assignment1.Verticles.Employee;
import com.omkar.assignment1.codec.LocalMessageCodec;
import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MainVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    public static void main(String[] args) {

        var vertx = Vertx.vertx(
                new VertxOptions()
                .setBlockedThreadCheckInterval(1)
                .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS));
        vertx.eventBus().registerDefaultCodec(Employee.class, new LocalMessageCodec<>(Employee.class));
        vertx.exceptionHandler(error -> LOG.error("Unhandled:",error));
        vertx.deployVerticle(new MainVerticle())
                .onFailure(err -> LOG.error("Failed to deploy:",err))
                .onSuccess(id -> LOG.info("Deployed {} with id {}",MainVerticle.class.getSimpleName(),id));
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.deployVerticle(RestApiVerticle.class.getName(),
                        new DeploymentOptions().setInstances(halfProcessors()))
                .onFailure(startPromise::fail)
                .onSuccess(id ->{
                    LOG.info("Deployed {} wit id {}",RestApiVerticle.class.getSimpleName(),id);
                    startPromise.complete();

                });
    }

    private int halfProcessors() {
        return Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
    }
}
