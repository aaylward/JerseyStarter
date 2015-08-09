package ninja.freethrow.jerseystarter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("this-is-nice")
public class TestResource {

  @GET
  public String getString() {
    return "Hello";
  }
}
