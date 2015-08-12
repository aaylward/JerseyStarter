package ninja.freethrow.jerseystarter;

import ninja.freethrow.jerseystarter.config.StartupConfiguration;

public interface Service {
  Service configure(StartupConfiguration configuration);
  void run();
  void run(boolean join);
  void stop();
}
