package com.omkar.assignment1.Verticles.Retrieve;

import com.omkar.assignment1.Verticles.CrudRestApi;
import com.omkar.assignment1.db.DbResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.templates.SqlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class GetFromDatabaseHandler implements Handler<RoutingContext> {
    private static final Logger LOG = LoggerFactory.getLogger(GetFromDatabaseHandler.class);
    private final Pool db;

    public GetFromDatabaseHandler(final Pool db) {
        this.db = db;
    }

    @Override
    public void handle(RoutingContext context) {
        var accountId = CrudRestApi.getAccountId(context);
/*
        db.query("SELECT a.employeename FROM public.employeedetails a where account_id=#{accountId}")
                .execute()
                .onFailure(DbResponse.errorHandler(context,"Failed to get data from db"))
                .onSuccess(result ->{
                    var response = new JsonArray();
                    result.forEach(row -> {
                        response.add(row.getValue("employeename"));
                    });
                    LOG.info("Path {} responds with {}",context.normalizedPath(),response.encode());
                    context.response()
                            .putHeader(HttpHeaders.CONTENT_TYPE,HttpHeaderValues.APPLICATION_JSON)
                            .end(response.toBuffer());
                });
*/
        SqlTemplate.forQuery(db,
                "SELECT w FROM public.employeedetails w where w.account_id=#{accountId}")
                .mapTo(Row::toJson)
                .execute(Collections.singletonMap("account_id", accountId))
                .onFailure(DbResponse.errorHandler(context, "Failed to fetch account details for accountId: " + accountId))
                .onSuccess(employee -> {
                   if(!employee.iterator().hasNext()){
                        DbResponse.notFound(context,"employee is not available");
                        return ;
                    }


                    var response = new JsonArray();
                  employee.forEach(response::add);
                    LOG.info("Path {} responds with {}", context.normalizedPath(), response.encode());
                    context.response()
                            .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                            .end(response.toBuffer());

                });



    }
}
