package com.example.vertx_starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleAB extends AbstractVerticle {

  private static final Logger Log = LoggerFactory.getLogger(VerticleAB.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Log.debug("Start {}",getClass().getName());
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    Log.debug("Stop {}",getClass().getName());
    stopPromise.complete();
  }
}
