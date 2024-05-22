package academy.quarkus.pizza.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Store extends PanacheEntity {

    public Long id;
    public String name;
    public String code;

    public Store () {}

    public static Store persist(String name, String code){
        var result = new Store();
        result.name = name;
        result.code = code;
        result.persist();
        return result;
    }
    public static Store findNearest() {
        return findNearest(Location.current());
    }

    public static Store findNearest(Location loc) {
        Store store = Store.find("code", "__default__").firstResult();
        return store;
    }
    
}
