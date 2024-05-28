package academy.quarkus.pizza.event;

import java.time.LocalDateTime;

import academy.quarkus.pizza.model.Ticket;

public record TicketSubmitted(Ticket ticket,
LocalDateTime submittedAt) {
    
}
