package ninja.freethrow.jerseystarter;

import java.util.Optional;

public class Configuration {
  private final int port;
  private final Package basePackage;
  private Optional<String> appRootMaybe = Optional.empty();

  public Configuration(int port,
                       Package basePackage,
                       Optional<String> appRootMaybe) {
    this.port = port;
    this.basePackage = basePackage;
    this.appRootMaybe = appRootMaybe;
  }

  public int getPort() {
    return port;
  }

  public Package getBasePackage() {
    return basePackage;
  }

  public Optional<String> getAppRoot() {
    return appRootMaybe;
  }
}
