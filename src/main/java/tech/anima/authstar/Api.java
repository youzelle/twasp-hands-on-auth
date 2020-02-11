package tech.anima.authstar;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Api {

    private final String HARDCODED_USER = "foo";
    private final AvailableTickets availableTickets;
    private final BoughtTickets boughtTickets;

    public Api(AvailableTickets availableTickets, BoughtTickets boughtTickets) {
        this.availableTickets = availableTickets;
        this.boughtTickets = boughtTickets;
    }

//    @PreAuthorize("hasAuthority('SCOPE_openid')")
    @GetMapping("/api/v0/available-tickets")
    @ResponseBody
    public List<Ticket> listAvailableTickets() {
        return availableTickets.listWithFullPrice();
    }

    @PostMapping("/api/v0/buy")
    public void buyTicket(@RequestBody Ticket ticket) {
        boughtTickets.addToUser(HARDCODED_USER, ticket);
    }

    @GetMapping("/api/v0/my-tickets")
    @ResponseBody
    public List<Ticket> listMyTickets() {
        return boughtTickets.listForUser(HARDCODED_USER);
    }

    @GetMapping("/api/v0/admin/tickets")
    @ResponseBody
    public List<Ticket> listAvailableTicketsBasePrice() {
        return availableTickets.listWithBasePrice();
    }

    @GetMapping("/api/v0/admin/fee")
    @ResponseBody
    public Fee getFee() {
        return new Fee(availableTickets.getFee());
    }

    @PostMapping("/api/v0/admin/fee")
    public void setFee(@RequestBody Fee fee) {
        availableTickets.setFee(fee.amount);
    }

    public static class Fee {

        
        public final int amount;

        public Fee(@JsonProperty("amount") int amount) {
            this.amount = amount;
        }

    }

}
