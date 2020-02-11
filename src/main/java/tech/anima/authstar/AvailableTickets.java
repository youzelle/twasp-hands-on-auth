package tech.anima.authstar;

import java.util.List;
import java.util.stream.Collectors;

public class AvailableTickets {
    
    private final List<Ticket> ticketsAndBasePrice;
    private int fee;

    public AvailableTickets(List<Ticket> ticketsAndBasePrice, int fee) {
        this.ticketsAndBasePrice = ticketsAndBasePrice;
        this.fee = fee;
    }
    

    public List<Ticket> listWithFullPrice() {
        return ticketsAndBasePrice.stream().map((ticket) -> new Ticket(ticket.destination, ticket.price + fee)).collect(Collectors.toList());
    }

    public List<Ticket> listWithBasePrice() {
        return ticketsAndBasePrice;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
    
}
