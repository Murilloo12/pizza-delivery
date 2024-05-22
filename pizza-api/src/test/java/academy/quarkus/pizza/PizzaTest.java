package academy.quarkus.pizza;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import academy.quarkus.pizza.model.Location;
import academy.quarkus.pizza.model.Store;
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

    
}
