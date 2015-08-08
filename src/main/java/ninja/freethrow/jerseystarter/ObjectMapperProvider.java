package ninja.freethrow.jerseystarter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
  final ObjectMapper defaultObjectMapper;

  public ObjectMapperProvider() {
    defaultObjectMapper = createDefaultMapper();
  }

  @Override
  public ObjectMapper getContext(Class<?> aClass) {
    return defaultObjectMapper;
  }

  private static ObjectMapper createDefaultMapper() {
    return new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
