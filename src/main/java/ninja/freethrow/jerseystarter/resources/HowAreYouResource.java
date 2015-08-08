package ninja.freethrow.jerseystarter.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("how-are-you")
@Produces(MediaType.APPLICATION_JSON)
public class HowAreYouResource {

  @GET
  public int getOne() {
    return 1;
  }
}
