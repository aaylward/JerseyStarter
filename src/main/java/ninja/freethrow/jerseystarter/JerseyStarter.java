package ninja.freethrow.jerseystarter;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import ninja.freethrow.jerseystarter.config.StartupConfiguration;
import ninja.freethrow.jerseystarter.providers.ObjectMapperProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.servlet.GuiceFilter;

public class JerseyStarter implements Service {
  private static final Logger LOG = LoggerFactory.getLogger(JerseyStarter.class);
  private static final String DEFAULT_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_PATH_SPEC = "/*";

  private Server server;
  private int port;
  private String contextPath;
  private ResourceConfig jersey;

  @Override
  public Service configure(StartupConfiguration configuration) {
    this.port = configuration.getPort();
    this.contextPath = configuration.getContextPath().orElse(DEFAULT_CONTEXT_PATH);
    this.jersey = buildResourceConfigWithBasePackage(configuration.getBasePackage());
    this.server = buildServer();
    return this;
  }

  @Override
  public void run() {
    run(true);
  }

  @Override
  public void run(boolean join) {
    LOG.info("vroom! vroom!");
    try {
      server.start();
      if (join) {
        server.join();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (server != null) {
        server.destroy();
        server = null;
      }
      LOG.info("kthxbye");
    }
  }

  private Server buildServer() {
    ServletContainer servletContainer = new ServletContainer(jersey);

    ServletHolder sh = new ServletHolder(servletContainer);
    Server server = new Server(port);
    ServletContextHandler context = new ServletContextHandler(
        ServletContextHandler.SESSIONS);
    context.setContextPath(contextPath);

    FilterHolder filterHolder = new FilterHolder(GuiceFilter.class);
    context.addFilter(filterHolder, DEFAULT_SERVLET_PATH_SPEC,
        EnumSet.allOf(DispatcherType.class));

    context.addServlet(sh, DEFAULT_SERVLET_PATH_SPEC);

    server.setHandler(context);
    return server;
  }

  private ResourceConfig buildResourceConfigWithBasePackage(Package basePackage) {
    return new ResourceConfig()
        .packages(basePackage.getName())
        .register(ObjectMapperProvider.class)
        .register(JacksonFeature.class);
  }
}
