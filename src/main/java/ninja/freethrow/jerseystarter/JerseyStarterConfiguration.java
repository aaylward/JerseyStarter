package ninja.freethrow.jerseystarter;

public class JerseyStarterConfiguration {
  private final int port;
  private final Package basePackage;

  public JerseyStarterConfiguration(int port, Package basePackage) {
    this.port = port;
    this.basePackage = basePackage;
  }

  public int getPort() {
    return port;
  }

  public Package getBasePackage() {
    return basePackage;
  }
}
