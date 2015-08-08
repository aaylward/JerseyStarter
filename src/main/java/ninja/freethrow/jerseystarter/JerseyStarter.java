package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JerseyStarter {
  private int port;
  private Server server;

  public static JerseyStarter newJerseyStarter(int port) {
    return new JerseyStarter(port);
  }

  public void run() throws Exception {
    JerseyStarterApplication application = JerseyStarterApplication
        .newStarterApplication(JerseyStarter.class.getPackage());
    URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
    server = JettyHttpContainerFactory.createServer(baseUri, application);
    server.start();
  }

  public static void main(String... args) throws Exception {
    newJerseyStarter(8080).run();
  }

  private JerseyStarter(int port) {
    this.port = port;
  }
}
