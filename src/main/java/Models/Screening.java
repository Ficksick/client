package Models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Screening implements Serializable {
    private int screening_id;
    private Film film;
    private Hall hall;
    private Date date;
    private Time startTime;
    private Time endTime;
    private double price;
    private static final long serialVersionUID = 345678912L;

    public Screening() {
    }

    public Screening(int id, Date date, Time start_time, Time end_time, double price) {
        this.screening_id = id;
        this.date = date;
        this.startTime = start_time;
        this.endTime = end_time;
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setScreening_id(int id) {
        this.screening_id = id;
    }

    public void setStart_time(Time time) {
        this.startTime = time;
    }

    public void setEnd_time(Time time) {
        this.endTime = time;
    }

    public int getScreening_id() {
        return screening_id;
    }

    public Film getFilm() {
        return this.film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return this.hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Time getStart_time() {
        return startTime;
    }

    public Time getEnd_time() {
        return endTime;
    }

    public String toString() {
        return "Номер сеана = " + screening_id +
                "\nНазвание фильма = " + film +
                "\nЗал = " + hall +
                "\nДата = " + date +
                "\nВремя начала = " + startTime +
                "\nВремя конца = " + endTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getFilmTitle() {
        return film.getTitle();
    }

    public int getHallID() {
        return hall.getHall_id();
    }

    public String getHallName() {
        return hall.getHallName();
    }
}
