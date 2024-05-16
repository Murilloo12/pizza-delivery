package academy.quarkus.pizza;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/pizza")
public class PizzaResource {

    @Transactional
    public void init(@Observes StartupEvent ev){
        var pizza1 = new Pizza();
        pizza1.description = "Peperoni";
        pizza1.persist();

        var pizza2 = new Pizza();
        pizza2.description = "Frango com Catupiry";
        pizza2.persist();
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pizza> getAll() {
        return Pizza.listAll();
    }
}
