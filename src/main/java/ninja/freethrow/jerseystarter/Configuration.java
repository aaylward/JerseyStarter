package ninja.freethrow.jerseystarter;

import java.util.Optional;

public class Configuration {
  private static final long DEFAULT_SHUTDOWN_GRACE_PERIOD_MILLIS = 10 * 1000;
  private final int port;
  private final Package basePackage;
  private Optional<String> appRootMaybe = Optional.empty();
  private long shutdownGracePeriod;

  public Configuration(int port,
                       Package basePackage,
                       Optional<String> appRootMaybe,
                       long shutdownGracePeriod) {
    this.port = port;
    this.basePackage = basePackage;
    this.appRootMaybe = appRootMaybe;
    this.shutdownGracePeriod = shutdownGracePeriod;
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

  public long getShutdownGracePeriod() {
    return shutdownGracePeriod > 0 ? shutdownGracePeriod : DEFAULT_SHUTDOWN_GRACE_PERIOD_MILLIS;
  }
}
