package ninja.freethrow.jerseystarter;

import com.hubspot.horizon.HttpClient;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.ContentType;
import com.hubspot.horizon.HttpRequest.Method;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.apache.ApacheHttpClient;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JerseyStarterTest {
  private static final int TEST_PORT = 8090;
  private static final HttpClient HTTP = new ApacheHttpClient();
  private static final String APP_ROOT = "/testing-is-fun";

  public JerseyStarterTest() throws Exception {
    Configuration configuration = new Configuration(TEST_PORT,
        JerseyStarterTest.class.getPackage(),
        Optional.of(APP_ROOT),
        0);
    JerseyStarter
        .newStarterApp(configuration)
        .buildServer()
        .run(false);
  }

  @Test
  public void itCanServeRequests() {
    String uri = String.format("http://localhost:%d%s/this-is-nice", TEST_PORT, APP_ROOT);
    HttpRequest request = HttpRequest.newBuilder()
        .setUrl(uri)
        .setMethod(Method.GET)
        .setAccept(ContentType.TEXT)
        .build();
    HttpResponse response = HTTP.execute(request);
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getAsString()).isEqualTo("Hello");
  }
}
