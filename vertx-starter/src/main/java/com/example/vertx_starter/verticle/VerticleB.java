package com.example.vertx_starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleB extends AbstractVerticle {
  private static final Logger Log = LoggerFactory.getLogger(VerticleB.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Log.debug("Start {}",getClass().getName());
    startPromise.complete();
  }
}
