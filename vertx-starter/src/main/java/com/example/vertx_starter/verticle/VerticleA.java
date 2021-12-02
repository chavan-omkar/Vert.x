package com.example.vertx_starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VerticleA extends AbstractVerticle {

  private static final Logger Log = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Log.debug("Start {}",getClass().getName());
    vertx.deployVerticle(new VerticleAA(),whenDeployed ->{
      Log.debug("Deployed {}",VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result()); // undeployed
    });
    vertx.deployVerticle(new VerticleAB(), whenDeployed ->{
      Log.debug("Deployed {}",VerticleAB.class.getName());
      //notundeployed
    });
    startPromise.complete();
  }
}
