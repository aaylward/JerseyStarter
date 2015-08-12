package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyStarter {
  private static final Logger LOG = LoggerFactory.getLogger(JerseyStarter.class);

  private Server server;
  private int port;
  private String contextPath;
  private Package basePackage;
  private ResourceConfig jersey;

  public static JerseyStarter newStarterApp(StartupConfiguration configuration) {
    return new JerseyStarter(configuration);
  }

  public JerseyStarter(StartupConfiguration configuration) {
    this.port = configuration.getPort();
    this.contextPath = configuration.getContextPath().orElse("/");
    this.basePackage = configuration.getBasePackage();
    this.jersey = buildResourceConfig(this.basePackage);
    buildServer();
  }

  public void run() throws Exception {
    run(true);
  }

  public void run(boolean join) throws Exception {
    LOG.info("vroom! vroom!");
    server.start();
    if (join) {
      server.join();
    }
  }

  public void stop() throws Exception {
    try {
      server.stop();
    } finally {
      if (server != null) {
        server.destroy();
      }
      LOG.info("kthxbye");
    }
  }

  private void buildServer() {
    server = new Server(port);

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(contextPath);

    server.setHandler(contextHandler);
    contextHandler.addServlet(new ServletHolder(new ServletContainer(jersey)), "/*");
  }

  private ResourceConfig buildResourceConfig(Package basePackage) {
    return new ResourceConfig()
        .packages(basePackage.getName())
        .register(ObjectMapperProvider.class)
        .register(JacksonFeature.class);
  }
}
