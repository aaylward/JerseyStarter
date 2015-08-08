package ninja.freethrow.jerseystarter;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JerseyStarter {
  public static void main(String... args) throws Exception {
    URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
    JerseyStarterApplication application = JerseyStarterApplication
        .newStarterApplication(JerseyStarter.class.getPackage());

    Server jettyServer = JettyHttpContainerFactory.createServer(baseUri, application);
    jettyServer.start();
  }
}
