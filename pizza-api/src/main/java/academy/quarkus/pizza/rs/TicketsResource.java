package academy.quarkus.pizza.rs;

import java.time.LocalDateTime;
import java.util.Map;

import academy.quarkus.pizza.event.TicketSubmitted;
import academy.quarkus.pizza.model.Ticket;
import academy.quarkus.pizza.model.TicketStatus;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/tickets")
@Transactional
public class TicketsResource {

    @Inject
    Event<TicketSubmitted> events;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Ticket createTicket(Map<String,Object> params){
        Long personId = ((Number) params.get("personId")).longValue();
        String addressMain = (String) params.get("addressMain");
        String addressDetail = (String) params.get("addressDetail");
        String phone = (String) params.get("phone");
        Ticket ticket = Ticket.persist(personId, phone, addressMain, addressDetail,status);
        return ticket;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket readTicket(@PathParam("id") Long id){
        return Ticket.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket deleteTicket(long id){
        Ticket t = readTicket(id);
        t.status = TicketStatus.DELETED;
        return null;
    }

    @PUT
    @Path("/{id}")
    public Ticket addItem(Long id, TicketItemAdd itemAdd){
        Ticket ticket = readTicket(id);
        if (ticket == null){
            throw new NotFoundException("Ticket Not Found");
        }
        if (! TicketStatus.OPEN.equals(ticket.status)){
            throw new BadRequestException("Ticket not open");
        }
        if (itemAdd.quantity().intValue() <= 0 || itemAdd.quantity().intValue() >= 99){
            throw new BadRequestException("Invalid Quantity");
        }ticket.addItem(itemAdd);
        return ticket;
    }

    @POST
    @Path("/{id}/submit")
    public Ticket submitTicket(Long id){
        Ticket ticket = readTicket(id);
        if (! TicketStatus.OPEN.equals(ticket.status)){
            throw new BadRequestException("Ticket not open");
        }
        ticket.status = TicketStatus.SUBMITED;
        ticket.persistAndFlush();
        events.fire(new TicketSubmitted(
            ticket,
            LocalDateTime.now()
        ));
        return ticket;
    }
}
