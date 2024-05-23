package academy.quarkus.pizza.model;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

@Entity
public class TicketItem extends PanacheEntity {
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    public Ticket ticket;

    @ManyToOne
    public Pizza pizza;

    public BigDecimal price;

    public Integer qty;

    public TicketItem () {}


    @Transactional()
    public static TicketItem persist(Ticket ticket, Pizza pizza, BigDecimal price, Integer qty){
        var result = new TicketItem();
        result.ticket = ticket;
        result.pizza = pizza;
        result.price = price;
        result.qty = qty;
        result.persist();
        return result;
    }

    @Transient
    public BigDecimal getValue(){
        return price.multiply(BigDecimal.valueOf(qty));
    }

}
