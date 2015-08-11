package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.MalformedURLException;
import java.net.URI;

public class JerseyStarterApplication extends ResourceConfig {
  private static final String BASE_URI = "http://localhost";

  private Server server;

  public JerseyStarterApplication configure(Configuration configuration) throws MalformedURLException {
    packages(configuration.getBasePackage().getName());
    register(ObjectMapperProvider.class);
    register(JacksonFeature.class);
    server = build(configuration);
    return this;
  }

  public JerseyStarterApplication run() throws Exception {
    return run(true);
  }

  public JerseyStarterApplication run(boolean join) throws Exception {
    server.start();
    if (join) {
      server.join();
    }
    return this;
  }

  public void stop() throws Exception {
    try {
      server.stop();
    } finally {
      if (server != null) {
        server.destroy();
      }
    }
  }

  private URI buildUri(Configuration configuration) {
    return UriBuilder
        .fromUri(BASE_URI)
        .port(configuration.getPort())
        .build();
  }

  private Server build(Configuration configuration) throws MalformedURLException {
    final URI baseUri = buildUri(configuration);
    Server server = JettyHttpContainerFactory.createServer(baseUri, this, false);
    return addHandlers(server, configuration);
  }

  private Server addHandlers(Server server, Configuration configuration) throws MalformedURLException {
    ServletContextHandler servletContextHandler = new ServletContextHandler(server, configuration.getAppRoot().orElse(""));

    HandlerList handlers = new HandlerList();
    handlers.setHandlers(new Handler[]
        {servletContextHandler, new DefaultHandler()});

    server.setHandler(handlers);
    return server;
  }
}
