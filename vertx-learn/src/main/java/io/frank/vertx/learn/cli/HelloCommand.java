package io.frank.vertx.learn.cli;

import io.frank.vertx.learn.util.VertxUtils;
import io.vertx.core.cli.CLIException;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Option;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.core.spi.launcher.DefaultCommand;

/**
 * @author jinjunliang 2019-07-16 15:05
 **/
@Name("hello")
@Summary("A simple hello command.")
public class HelloCommand extends DefaultCommand {
  private String name;

  /**
   * 必须定义为set方法
   * @param n
   */
  @Option(longName = "name", required = true)
  public void setName(String n) {
    this.name = n;
  }

  @Override
  public void run() throws CLIException {
    VertxUtils.send().setHandler(ar -> {
      System.out.println("hello " + name);
      System.exit(1);
    });
  }
}
