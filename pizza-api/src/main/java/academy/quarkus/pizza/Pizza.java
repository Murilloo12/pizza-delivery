package academy.quarkus.pizza;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Pizza extends PanacheEntity {
    public String description;
}