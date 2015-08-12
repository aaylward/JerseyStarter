package ninja.freethrow.jerseystarter;

import ninja.freethrow.jerseystarter.config.StartupConfiguration;

public interface Service {
  Service configure(StartupConfiguration configuration);
  void run() throws Exception;
  void run(boolean join) throws Exception;
  void stop() throws Exception;
}
