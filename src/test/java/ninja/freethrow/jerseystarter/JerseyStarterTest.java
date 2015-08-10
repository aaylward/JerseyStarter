package ninja.freethrow.jerseystarter;

import com.hubspot.horizon.HttpClient;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.ContentType;
import com.hubspot.horizon.HttpRequest.Method;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.apache.ApacheHttpClient;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JerseyStarterTest extends JerseyStarterApplication {
  private static final int TEST_PORT = 8090;
  private static final HttpClient HTTP = new ApacheHttpClient();

  public JerseyStarterTest() throws Exception {
    super();
    Configuration configuration = new Configuration(TEST_PORT,
        JerseyStarterTest.class.getPackage(),
        Optional.of("test"),
        0);
    configure(configuration);
    run(false);
  }

  @Test
  @Ignore
  public void itCanServeRequests() {
    String uri = String.format("http://localhost:%d/this-is-nice", TEST_PORT);
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
