package ninja.freethrow.jerseystarter.config;

import java.util.Optional;

public class StartupConfiguration {
  public static final String PORT_PROPERTY_NAME = "JERSEY_STARTER_PORT";
  public static final String CONTEXT_PATH_PROPERTY_NAME = "JERSEY_STARTER_CONTEXT_PATH";

  private final Integer port;
  private final Package basePackage;
  private Optional<String> contextPathMaybe;

  public StartupConfiguration(int port,
                              Package basePackage,
                              String contextPath) {
    this.port = port;
    this.basePackage = basePackage;
    this.contextPathMaybe = Optional.ofNullable(contextPath);
  }

  public static StartupConfiguration fromEnvWithBasePackage(Package basePackage) {
    Integer port = parsePropertyAsInteger(PORT_PROPERTY_NAME);
    String appRoot = System.getProperty(CONTEXT_PATH_PROPERTY_NAME);
    return new StartupConfiguration(port, basePackage, appRoot);
  }

  public int getPort() {
    return port;
  }

  public Package getBasePackage() {
    return basePackage;
  }

  public Optional<String> getContextPath() {
    return contextPathMaybe;
  }

  private static Integer parsePropertyAsInteger(String propertyName) {
    try {
      return Integer.parseInt(System.getProperty(propertyName));
    } catch (NumberFormatException n) {
      throw new RuntimeException(n);
    }
  }
}