package ninja.freethrow.jerseystarter;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyStarterResourceConfiguration extends ResourceConfig {
  public JerseyStarterResourceConfiguration configure(Configuration configuration) {
    packages(configuration.getBasePackage().getName());
    register(ObjectMapperProvider.class);
    register(JacksonFeature.class);
    return this;
  }
}
