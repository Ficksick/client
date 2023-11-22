package Models;

import java.sql.Time;

public class Screening {
    private int screening_id;
    private int film_id;
    private int hall_id;
    private Time start_time;
    private Time end_time;

    public Screening() {
    }

    public Screening(int id, int film_id, int hall_id, Time start_time, Time end_time) {
        this.screening_id = id;
        this.film_id = film_id;
        this.hall_id = hall_id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void setScreening_id(int id) {
        this.screening_id = id;
    }

    public void setHall_id(int id) {
        this.hall_id = id;
    }

    public void setFilm_id(int id) {
        this.film_id = id;
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

    public int getHall_id() {
        return hall_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public Time getStart_time() {
        return start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }
}
