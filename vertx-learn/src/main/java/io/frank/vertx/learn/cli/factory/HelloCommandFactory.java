package io.frank.vertx.learn.cli.factory;

import io.frank.vertx.learn.cli.HelloCommand;
import io.vertx.core.spi.launcher.DefaultCommandFactory;

/**
 * @author jinjunliang 2019-07-16 15:09
 **/
public class HelloCommandFactory extends DefaultCommandFactory<HelloCommand> {

  public HelloCommandFactory() {
    super(HelloCommand.class);
  }
}
