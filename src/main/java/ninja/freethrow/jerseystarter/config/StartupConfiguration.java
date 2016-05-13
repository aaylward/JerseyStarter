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
    String appRoot = normalizeAppRoot(findProperty(CONTEXT_PATH_PROPERTY_NAME));
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

  private static String normalizeAppRoot(String rawAppRoot) {
    if (rawAppRoot == null || "/".equals(rawAppRoot)) {
      return "/";
    }

    if (rawAppRoot.startsWith("/")) {
      return rawAppRoot;
    }

    return "/" + rawAppRoot;
  }

  private static String findProperty(String propertyName) {
    String property = System.getProperty(propertyName);
    if (property == null) {
      property = System.getenv(propertyName);
    }
    if (property == null) {
      throw new IllegalArgumentException("Couldn't find property: " + propertyName);
    }
    return property;
  }

  private static Integer parsePropertyAsInteger(String propertyName) {
    try {
      return Integer.parseInt(findProperty(propertyName));
    } catch (NumberFormatException n) {
      throw new RuntimeException("Failed to parse '" + propertyName + "' as an integer");
    }
  }
}
