package ninja.freethrow.jerseystarter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloResource {

  @GET
  public String sayHi(@QueryParam("name") String name) {
    return "Hello " + name;
  }

  @GET
  @Path("bop")
  @Produces(MediaType.APPLICATION_JSON)
  public Map<String, String> getJSON() {
    Map<String, String> map = new HashMap<>();
    map.put("apples", "are good");
    return map;
  }
}
