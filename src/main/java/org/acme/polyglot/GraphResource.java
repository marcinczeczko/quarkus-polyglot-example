package org.acme.polyglot;

import io.quarkus.runtime.StartupEvent;
import java.nio.charset.Charset;
import java.util.concurrent.CompletionStage;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/graph")
public class GraphResource {

  private static final Logger LOGGER = LoggerFactory.getLogger("GraphResource");

  @Inject
  SvgRGraphService service;

  private String template;

  @GET
  @Produces(MediaType.TEXT_HTML)
  public CompletionStage<String> home() {
    return service.hello().thenApply(svg -> template.replace("${graph}", svg));
  }

  void onStart(@Observes StartupEvent ev) {
    try {
      template = IOUtils.toString(getClass().getResource("graph.html").toURI(), Charset.defaultCharset());
    } catch (Exception e) {
      LOGGER.error("Ups!! Something went wrong", e);
      template = "";
    }
  }

}