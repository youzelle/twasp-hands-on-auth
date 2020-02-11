package tech.anima.authstar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoughtTickets {

    private final Map<String, List<Ticket>> usersToTickets = new HashMap<>();

    public List<Ticket> listForUser(String userId) {
        return usersToTickets.getOrDefault(userId, Collections.emptyList());
    }

    public void addToUser(String userId, Ticket ticket) {
        usersToTickets.computeIfAbsent(userId, (id) -> new ArrayList<>()).add(ticket);
    }

}
