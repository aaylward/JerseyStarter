package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JerseyStarter {
  private static final Logger LOG = LoggerFactory.getLogger(JerseyStarter.class);
  private static final String BASE_URI_FORMAT = "http://localhost/%s";

  private final Server server;

  public static JerseyStarter newJerseyStarter(Configuration configuration) {
    return new JerseyStarter(configuration);
  }

  public void run() throws Exception {
    LOG.info("vroom! vroom!");
    server.start();
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

  public JerseyStarter(Configuration configuration) {
    URI baseUri = buildUri(configuration);
    JerseyStarterApplication application = buildApplication(configuration);
    server = JettyHttpContainerFactory.createServer(baseUri, application, false);
  }

  private URI buildUri(Configuration configuration) {
    return UriBuilder
        .fromUri(String.format(BASE_URI_FORMAT, configuration.getAppRoot().orElse("")))
        .port(configuration.getPort())
        .build();
  }

  private JerseyStarterApplication buildApplication(Configuration configuration) {
    return JerseyStarterApplication.newStarterApplication(configuration.getBasePackage());
  }
}
