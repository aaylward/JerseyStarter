package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JerseyStarter {
  private final Server server;

  public static JerseyStarter newJerseyStarter(JerseyStarterConfiguration configuration) {
    return new JerseyStarter(configuration);
  }

  public void run() throws Exception {
    server.start();
  }

  public static void main(String... args) throws Exception {
    JerseyStarterConfiguration configuration = new JerseyStarterConfiguration(8080, JerseyStarter.class.getPackage());
    newJerseyStarter(configuration).run();
  }

  private JerseyStarter(JerseyStarterConfiguration configuration) {
    JerseyStarterApplication application = JerseyStarterApplication
        .newStarterApplication(configuration.getBasePackage());
    URI baseUri = UriBuilder.fromUri("http://localhost/").port(configuration.getPort()).build();
    server = JettyHttpContainerFactory.createServer(baseUri, application);
  }
}
