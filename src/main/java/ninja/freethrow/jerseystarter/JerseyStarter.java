package ninja.freethrow.jerseystarter;

import ninja.freethrow.jerseystarter.config.StartupConfiguration;
import ninja.freethrow.jerseystarter.providers.ObjectMapperProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyStarter implements Service {
  private static final Logger LOG = LoggerFactory.getLogger(JerseyStarter.class);
  private static final String DEFAULT_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_PATH_SPEC = "/*";

  private Server server;
  private int port;
  private String contextPath;
  private ResourceConfig jersey;

  @Override
  public Service configure(StartupConfiguration configuration) {
    this.port = configuration.getPort();
    this.contextPath = configuration.getContextPath().orElse(DEFAULT_CONTEXT_PATH);
    this.jersey = buildResourceConfigWithBasePackage(configuration.getBasePackage());
    this.server = buildServer();
    return this;
  }

  @Override
  public void run() {
    run(true);
  }

  @Override
  public void run(boolean join) {
    LOG.info("vroom! vroom!");
    try {
      server.start();
      if (join) {
        server.join();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (server != null) {
        server.destroy();
        server = null;
      }
      LOG.info("kthxbye");
    }
  }

  private Server buildServer() {
    Server server = new Server(port);

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(contextPath);

    server.setHandler(contextHandler);
    contextHandler.addServlet(new ServletHolder(new ServletContainer(jersey)), DEFAULT_SERVLET_PATH_SPEC);
    return server;
  }

  private ResourceConfig buildResourceConfigWithBasePackage(Package basePackage) {
    return new ResourceConfig()
        .packages(basePackage.getName())
        .register(ObjectMapperProvider.class)
        .register(JacksonFeature.class);
  }
}
