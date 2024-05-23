package academy.quarkus.pizza.dev;

import academy.quarkus.pizza.model.Category;
import academy.quarkus.pizza.model.Pizza;
import academy.quarkus.pizza.model.Store;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class SampleDataInit {
    @Inject
    LaunchMode mode;

    @Transactional
    public void initi(@Observes StartupEvent ev) {
        if(LaunchMode.NORMAL.equals(mode)) return;
        var store = Store.persist("Pizza Shack", "__default__");

        var trad = Category.persist(store, "Traditional", "10.99");
        var pepe = Pizza.persist("Peperoni");
        var franc = Pizza.persist("Franco com Catupiry");
        trad.addPizzas(pepe,franc);

        var premium = Category.persist(store, "Premium", "14.99");
        var banana = Pizza.persist("Banana");
        var caramelita = Pizza.persist("Caramelita");
        var mussarela = Pizza.persist("Mussarela");
        premium.addPizzas(banana,caramelita,mussarela);
    }

}
