package ninja.freethrow.jerseystarter;

import ninja.freethrow.jerseystarter.config.StartupConfiguration;

public interface Service {
  static Service buildService(Package basePackage) {
    StartupConfiguration configuration = StartupConfiguration
        .fromEnvWithBasePackage(basePackage);
    return new JerseyStarter().configure(configuration);
  }

  Service configure(StartupConfiguration configuration);
  void run();
  void run(boolean join);
  void stop();
}
