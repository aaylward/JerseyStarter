package ninja.freethrow.jerseystarter;

import java.util.Optional;

public class StartupConfiguration {
  private final int port;
  private final Package basePackage;
  private Optional<String> contextPathMaybe;

  public StartupConfiguration(int port,
                              Package basePackage,
                              String contextPath) {
    this.port = port;
    this.basePackage = basePackage;
    this.contextPathMaybe = Optional.ofNullable(contextPath);
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
}
