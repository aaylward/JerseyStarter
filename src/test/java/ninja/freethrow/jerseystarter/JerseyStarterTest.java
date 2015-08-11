package ninja.freethrow.jerseystarter;

import com.hubspot.horizon.HttpClient;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.ContentType;
import com.hubspot.horizon.HttpRequest.Method;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.apache.ApacheHttpClient;
import ninja.freethrow.jerseystarter.resources.JSONResource.MyThing;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JerseyStarterTest {
  private static final String APP_ROOT = "/testing-is-fun";
  private static final int TEST_PORT = 8090;
  private static final Configuration config = new Configuration(TEST_PORT,
        JerseyStarterTest.class.getPackage(),
        Optional.of(APP_ROOT),
        0);
  private static final JerseyStarter jersey = JerseyStarter.newStarterApp(config).buildServer();
  private static final HttpClient HTTP = new ApacheHttpClient();

  @BeforeClass
  public static void setup() throws Exception {
    jersey.run(false);
  }

  @AfterClass
  public static void cleanup() throws Exception {
    jersey.stop();
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

  @Test
  public void itCanServeJSON() {
    MyThing expected = new MyThing("bop");
    String uri = String.format("http://localhost:%d%s/gimme-json", TEST_PORT, APP_ROOT);
    HttpRequest request = HttpRequest.newBuilder()
        .setUrl(uri)
        .setQueryParam("name").to("bop")
        .setMethod(Method.GET)
        .setAccept(ContentType.JSON)
        .build();
    HttpResponse response = HTTP.execute(request);
    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getAs(MyThing.class)).isEqualTo(expected);
  }
}
