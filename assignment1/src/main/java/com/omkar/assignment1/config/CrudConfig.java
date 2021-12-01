package com.omkar.assignment1.config;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Objects;

@Builder
@Value
@ToString
public class CrudConfig {
    int serverPort;
    DbConfig dbConfig;

    public static CrudConfig from(final JsonObject config){
        final Integer serverPort = config.getInteger(ConfigLoader.SERVER_PORT);
        if (Objects.isNull(serverPort)) {
            throw new RuntimeException(ConfigLoader.SERVER_PORT + " not configured!");
        }
        return CrudConfig.builder()
                .serverPort(serverPort)
                .dbConfig(parseDbConfig(config))
                .build();
    }

    private static DbConfig parseDbConfig(final JsonObject config) {
        return DbConfig.builder()
                .host(config.getString(ConfigLoader.DB_HOST))
                .port(config.getInteger(ConfigLoader.DB_PORT))
                .database(config.getString(ConfigLoader.DB_DATABASE))
                .user(config.getString(ConfigLoader.DB_USER))
                .password(config.getString(ConfigLoader.DB_PASSWORD))
                .build();
    }

}
