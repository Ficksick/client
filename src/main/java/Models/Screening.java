package Models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Screening implements Serializable {
    private int screening_id;
    private Film film;
    private Hall hall;
    private Date date;
    private Time start_time;
    private Time end_time;
    private static final long serialVersionUID = 345678912L;

    public Screening() {
    }

    public Screening(int id, Date date, Time start_time, Time end_time) {
        this.screening_id = id;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }

    public void setScreening_id(int id) {
        this.screening_id = id;
    }

    public void setStart_time(Time time) {
        this.start_time = time;
    }

    public void setEnd_time(Time time) {
        this.end_time = time;
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
        return start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }
}
