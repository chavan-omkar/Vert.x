package com.example.vertx_starter.eventloops;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {

  private static final Logger Log = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
        .setEventLoopPoolSize(2)
    );
    vertx.deployVerticle(EventLoopExample.class.getName(),new DeploymentOptions().setInstances(4));
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {

    Log.debug("Start {}",getClass().getName());
    startPromise.complete();
    //do not do this in verticle
   // Thread.sleep(5000);

  }
}
