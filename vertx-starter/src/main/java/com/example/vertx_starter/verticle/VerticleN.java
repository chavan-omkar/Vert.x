package com.example.vertx_starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle {

  private static final Logger Log = LoggerFactory.getLogger(VerticleN.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
  //  System.out.println("Start"+getClass().getName()+"on thread"+Thread.currentThread().getName());

    Log.debug("Start {} with config {}",getClass().getName(),config().toString());
   // System.out.println("Start"+getClass().getName()+"with Config"+config().toString());
    startPromise.complete();
  }
}
