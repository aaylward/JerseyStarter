package ninja.freethrow.jerseystarter;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyStarterApplication extends ResourceConfig {

  public static JerseyStarterApplication newStarterApplication(Package toScan) {
    return new JerseyStarterApplication(toScan.getName());
  }

  private JerseyStarterApplication(String toScan) {
    packages(toScan);
    register(ObjectMapperProvider.class);
    register(JacksonFeature.class);
  }
}
