package academy.quarkus.pizza.data;

import academy.quarkus.pizza.model.Category;
import academy.quarkus.pizza.model.Courier;
import academy.quarkus.pizza.model.Delivery;
import academy.quarkus.pizza.model.Person;
import academy.quarkus.pizza.model.Pizza;
import academy.quarkus.pizza.model.Store;
import academy.quarkus.pizza.model.Ticket;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class TestDataProducer {
    public Delivery delivery;


    public void init (@Observes StartupEvent ev){
        Store store = Store.persist("Testing Store", "__TEST__");

        Category trad = Category.persist(store, "Traditional", "10.99");
        
        Pizza marg = Pizza.persist("Marguerita");

        Pizza mush = Pizza.persist("Mushrooms");

        trad.addPizzas(marg,mush);

        Person person = Person.persist("Winona Courier", "cool@girl.movie", "+55119999999999");

        Courier courier = Courier.persist(person.id, "1234567890");

        Ticket ticket = Ticket.persist(person, "Rabbit Hole 1", "Tea Room", null, null);

        delivery = Delivery.persist(ticket.id, store.id, courier.id);
    }


    @Produces
    public Delivery createDelivery () {
        return null;
    }
    
}
