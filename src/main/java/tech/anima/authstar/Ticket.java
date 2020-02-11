package tech.anima.authstar;

public class Ticket {

    public final String destination;
    public final int price; // do not use ints for prices in real life plz

    public Ticket(String destination, int totalPrice) {
        this.destination = destination;
        this.price = totalPrice;
    }

}
