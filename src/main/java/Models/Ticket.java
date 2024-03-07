package Models;

import javax.persistence.*;
import java.io.Serializable;

public class Ticket implements Serializable {
    private int id;
    private Screening screening;
    private int seatNumber;
    private double price;
    private String filmTitle;
    private String hallName;
    private User user;
    private static final long serialVersionUID = 678912345L;

    public Ticket() {

    }

    public Ticket(int id, int seatNumb, double price /*, String title, String hall*/) {
        this.id = id;
        //this.filmTitle = title;
        this.seatNumber = seatNumb;
        this.price = price;
        //this.hallName = hall;
    }

    public String getFilmTitle() {
        return screening.getFilmTitle();
    }

    public String getHallName() {
        return screening.getHall().getHallName();
    }

    public User getUser() {
        return this.user;
    }

    public double getPrice() {
        return price;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Screening getScreening() {
        return this.screening;
    }

    public int getId() {
        return id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

//    public void setHallName(String name) {
//        this.hallName = name;
//    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSeatNumber(int numb) {
        this.seatNumber = numb;
    }

//    public void setFilmTitle(String title) {
//        this.filmTitle = title;
//    }

    public void setId(int id){
        this.id = id;
    }

    public String toString() {
        return "Номер билета = " + id +
                "\nСеанс = " + screening +
                "\nЦена билета = " + price +
                "\nНомер места = " + seatNumber;
    }
}
