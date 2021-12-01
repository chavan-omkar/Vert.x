package com.omkar.assignment1.Verticles;

import com.omkar.assignment1.Verticles.Delete.DeleteFromDatabaseHandler;
import com.omkar.assignment1.Verticles.Insert.PostInDatabaseHandler;
import com.omkar.assignment1.Verticles.Retrieve.GetFromDatabaseHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrudRestApi {

    private static final Logger LOG = LoggerFactory.getLogger(CrudRestApi.class);

    public static void attach(final Router restApi,final Pool db) {

        final String pgPath ="/pg/employee/:accountId";
        restApi.get(pgPath).handler(new GetFromDatabaseHandler(db));
            restApi.delete(pgPath).handler(new DeleteFromDatabaseHandler(db));

      //  restApi.post("/pg/employee/:accountId/:employeename").handler(new PostInDatabaseHandler(db));
    }

    public static String getAccountId(final RoutingContext context){
        var accountId = context.pathParam("accountId");
        LOG.debug("{} for account {}",context.normalizedPath(),accountId);
        return accountId;
    }

    public static String getEmployeeName(final RoutingContext context){
        var employeeName = context.pathParam("employeename");
       return employeeName;
    }
}
