package com.omkar.assignment1.Verticles.Insert;

import com.omkar.assignment1.Verticles.CrudRestApi;
import com.omkar.assignment1.Verticles.Retrieve.WatchList;
import com.omkar.assignment1.db.DbResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.SqlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostInDatabaseHandler implements Handler<RoutingContext> {
    private static final Logger LOG = LoggerFactory.getLogger(PostInDatabaseHandler.class);
    private final Pool db;

    public PostInDatabaseHandler(final Pool db) {
        this.db = db;
    }

    @Override
    public void handle(RoutingContext context) {

        var accountId = CrudRestApi.getAccountId(context);
        var employeeName = CrudRestApi.getEmployeeName(context);
        var json = context.getBodyAsJson();
        var watchList = json.mapTo(WatchList.class);

        var parameterBatch = watchList.getAssets().stream()
                .map(asset -> {
                    final Map<String, Object> parameters = new HashMap<>();
                    parameters.put("account_id", accountId);
                    parameters.put("employeeName", employeeName);
                    return parameters;
                }).collect(Collectors.toList());

         SqlTemplate.forUpdate(db,
                        "INSERT INTO public.employeedetails(account_id,employeename) VALUES (#{account_id},#{employeename})"
//                                + " ON CONFLICT (account_id, employeename) DO NOTHING"
                )
                .executeBatch(parameterBatch)
                .onFailure(DbResponse.errorHandler(context, "Failed to insert into watchlist"))
                 .onSuccess( result -> {
                     var response = new JsonObject();
                     LOG.info("Path {} responds with {}", context.normalizedPath(), response.encode());

        context.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .end(response.toBuffer());
                 });

    }
}
