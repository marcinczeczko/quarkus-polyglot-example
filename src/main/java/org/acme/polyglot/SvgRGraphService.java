package org.acme.polyglot;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.graalvm.polyglot.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SvgRGraphService {

  private static final Logger LOGGER = LoggerFactory.getLogger("SvgRGraphService");

  private Context context;

  public CompletionStage<String> hello() {
    return CompletableFuture.supplyAsync(
        () -> {
          LOGGER.info("Start R script eval");
          return context.eval("R",
              "svg();\n"
                  + "     require(lattice);\n"
                  + "     x <- 1:100\n"
                  + "     y <- sin(x/10)\n"
                  + "     z <- cos(x^1.3/(runif(1)*5+10))\n"
                  + "     print(cloud(x~y*z, main=\"cloud plot\"))\n"
                  + "     grDevices:::svg.off()");
        })
        .thenApply(val -> val.asString());
  }

  void onStart(@Observes StartupEvent ev) {
    context = Context.newBuilder().allowAllAccess(true).build();
    LOGGER.info("Initialized R context");
  }

  void onStop(@Observes ShutdownEvent ev) {
    if (context != null) {
      context.close(true);
      LOGGER.info("Closed R context");
    }
  }

}
