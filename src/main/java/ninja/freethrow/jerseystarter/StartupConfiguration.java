package ninja.freethrow.jerseystarter;

import java.util.Optional;

public class StartupConfiguration {
  private final int port;
  private final Package basePackage;
  private Optional<String> contextPathMaybe = Optional.empty();

  public StartupConfiguration(int port,
                              Package basePackage,
                              Optional<String> contextPathMaybe) {
    this.port = port;
    this.basePackage = basePackage;
    this.contextPathMaybe = contextPathMaybe;
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
