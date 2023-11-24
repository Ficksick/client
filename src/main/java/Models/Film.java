package Models;

import java.io.Serializable;
import java.sql.Time;

public class Film implements Serializable {
    private int film_id;
    private String title;
    private String genre;
    private String director;
    private String mainActor;
    private Time duration;
    private int age;
    private static final long serialVersionUID = 456789123L;

    public Film() {
    }

    public Film(int id, String title, String genre, String director, String mainActor, Time duration, int age) {
        this.film_id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.mainActor = mainActor;
        this.duration = duration;
        this.age = age;
    }

    public int getFilmId() {
        return film_id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getMainActor() {
        return mainActor;
    }

    public String getDirector() {
        return director;
    }

    public int getAge() {
        return age;
    }

    public Time getDuration() {
        return duration;
    }

    public void setFilm_id(int id) {
        this.film_id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
