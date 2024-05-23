package academy.quarkus.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import academy.quarkus.pizza.model.Category;
import academy.quarkus.pizza.model.Location;
import academy.quarkus.pizza.model.Person;
import academy.quarkus.pizza.model.Pizza;
import academy.quarkus.pizza.model.Store;
import academy.quarkus.pizza.model.Ticket;
import academy.quarkus.pizza.rs.PizzaResource;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class PizzaTest {

    @Inject
    PizzaResource pizzas;

    @BeforeAll
    @Transactional
    public static void beforeAll(){
        var store = new Store();
        store.name = "Pizza Shack";
        store.code = "__default__";
        store.persist();
    }

    @Test 
    public void testFindNearestStore(){

        //GIVEN
        var location = Location.current();
        //WHEN
        var store = Store.findNearest(location);
        //THEN
        assertNotNull(store);

        Log.infof(store.id + " " + store.name);

    }

    @Test
    public void testAddToTicket(){
        //GIVEN
        var store = Store.persist("Pizza Shack", "__test__");

        var trad = Category.persist(store, "Traditional", "10.99");
        var pepe = Pizza.persist("Peperoni");
        var franc = Pizza.persist("Franco com Catupiry");
        trad.addPizzas(pepe,franc);
        var murillo = Person.persist("Murillo", "murillo@globalcode.com", "1111111111");
        //WHEN
        var ticket = Ticket.persist(murillo, "Rua do limoeiro", "casa 300");
        ticket.addItem(pepe, trad.price, 2);
        ticket.addItem(franc, trad.price, 1);
        var ticketValue = ticket.getValeu();
        //THEN
        var expectedValue = new BigDecimal("32.97");
        assertEquals(expectedValue, ticketValue);
    }
}
