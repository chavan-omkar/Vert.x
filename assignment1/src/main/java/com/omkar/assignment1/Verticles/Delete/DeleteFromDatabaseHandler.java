package com.omkar.assignment1.Verticles.Delete;

import com.omkar.assignment1.Verticles.CrudRestApi;
import com.omkar.assignment1.db.DbResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.templates.SqlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class DeleteFromDatabaseHandler implements Handler<RoutingContext> {

   private static final Logger LOG = LoggerFactory.getLogger(DeleteFromDatabaseHandler.class);
   private final Pool db;

    public DeleteFromDatabaseHandler(final Pool db) {
        this.db = db;
    }

    @Override
    public void handle(final RoutingContext context) {
        var accountId = CrudRestApi.getAccountId(context);

        SqlTemplate.forUpdate(db,
                "DELETE FROM public.employeedetails where account_id=#{accountId}")
                .execute(Collections.singletonMap("account_id",accountId))
                .onFailure(DbResponse.errorHandler(context,"Failed to delete employeedetails for acoountId: "+accountId))
                .onSuccess(result ->{
                    LOG.debug("Deleted {} rows for accountId {}",result.rowCount(),accountId);
                    context.response()
                            .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
                            .end();
                });

    }
}
