package com.omkar.assignment1.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ConfigLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);

    public static final String CONFIG_FILE = "application.yml";
    // Exposed Environment Variables
    public static final String SERVER_PORT = "SERVER_PORT";
    public static final String DB_HOST = "DB_HOST";
    public static final String DB_PORT = "DB_PORT";
    public static final String DB_DATABASE = "DB_DATABASE";
    public static final String DB_USER = "DB_USER";
    public static final String DB_PASSWORD = "DB_PASSWORD";
    static final List<String> EXPOSED_ENVIRONMENT_VARIABLES = Arrays.asList(SERVER_PORT,
            DB_HOST, DB_PORT, DB_DATABASE, DB_USER, DB_PASSWORD);

    public static Future<Object> load(Vertx vertx) {
        final var exposedKeys = new JsonArray();
        EXPOSED_ENVIRONMENT_VARIABLES.forEach(exposedKeys::add);
        LOG.debug("Fetch configuration for {}", exposedKeys.encode());

        var envStore = new ConfigStoreOptions()
                .setType("env")
                .setConfig(new JsonObject().put("keys", exposedKeys));

        var retriever = ConfigRetriever.create(vertx,
                new ConfigRetrieverOptions()
                        // Order defines overload rules
                        .addStore(envStore)
        );

        return retriever.getConfig().map(CrudConfig::from);
    }
}
