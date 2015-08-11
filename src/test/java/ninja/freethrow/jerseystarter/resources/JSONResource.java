package ninja.freethrow.jerseystarter.resources;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("gimme-json")
@Produces(MediaType.APPLICATION_JSON)
public class JSONResource {

  @GET
  public MyThing getThing(@QueryParam("name") String name) {
    return new MyThing(name);
  }

  public static class MyThing {
    private String name;

    public MyThing() {
    }

    public MyThing(String name) {
      this.name = name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (!(object instanceof MyThing)) {
        return false;
      }
      MyThing o = (MyThing)object;
      return getName().equals(o.getName());
    }
  }
}
