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
  private static final int TEST_PORT = 8090;
  private static JerseyStarter jerseyStarter;
  private static final HttpClient HTTP = new ApacheHttpClient();
  private static final Optional<String> BASE_PATH = Optional.of("test");

  @BeforeClass
  public static void setUp() throws Exception {
    Configuration configuration = new Configuration(TEST_PORT, JerseyStarterTest.class.getPackage(), BASE_PATH);
    jerseyStarter = JerseyStarter
        .newJerseyStarter(configuration);
    jerseyStarter.run();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    jerseyStarter.stop();
  }

  @Test
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

  @Test
  public void itCanServeJSON() {
    MyThing expected = new MyThing("bop");
    String uri = String.format("http://localhost:%d/gimme-json", TEST_PORT);
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
