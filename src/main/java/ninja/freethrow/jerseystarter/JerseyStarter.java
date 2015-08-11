package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JerseyStarter {
  private static final Logger LOG = LoggerFactory.getLogger(JerseyStarter.class);

  private Server server;
  private Configuration configuration;
  private JerseyStarterResourceConfiguration jersey;

  public static JerseyStarter newStarterApp(Configuration configuration) {
    JerseyStarterResourceConfiguration jersey = new JerseyStarterResourceConfiguration()
        .configure(configuration);
    return new JerseyStarter(configuration, jersey);
  }

  public JerseyStarter(Configuration configuration, JerseyStarterResourceConfiguration jersey) {
    this.configuration = configuration;
    this.jersey = jersey;
  }

  public JerseyStarter buildServer() {
    server = new Server(configuration.getPort());

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(configuration.getAppRoot().orElse("/"));

    server.setHandler(contextHandler);
    contextHandler.addServlet(new ServletHolder(new ServletContainer(jersey)), "/*");
    return this;
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
}
