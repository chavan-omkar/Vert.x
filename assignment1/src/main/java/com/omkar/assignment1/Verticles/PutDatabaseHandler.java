package com.omkar.assignment1.Verticles;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PutDatabaseHandler implements Handler<RoutingContext> {

    private static final Logger LOG = LoggerFactory.getLogger(PutDatabaseHandler.class);
    private final Pool db;

    public PutDatabaseHandler(final Pool db) {
        this.db = db;
    }

    @Override
    public void handle(RoutingContext context) {

    }
}
