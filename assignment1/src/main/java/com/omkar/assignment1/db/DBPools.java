package com.omkar.assignment1.db;

import com.omkar.assignment1.config.CrudConfig;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;

public class DBPools {
    private DBPools(){}

    public static Pool createPgPool(final CrudConfig configuration, final Vertx vertx) {
        final var connectOptions = new PgConnectOptions()
                .setHost(configuration.getDbConfig().getHost())
                .setPort(configuration.getDbConfig().getPort())
                .setDatabase(configuration.getDbConfig().getDatabase())
                .setUser(configuration.getDbConfig().getUser())
                .setPassword(configuration.getDbConfig().getPassword());

        var poolOptions = new PoolOptions()
                .setMaxSize(4);

        return PgPool.pool(vertx, connectOptions, poolOptions);
    }
}
