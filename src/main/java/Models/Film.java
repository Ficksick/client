package Models;

import java.sql.Time;

public class Film {
    private int film_id;
    private String title;
    private String genre;
    private String director;
    private String mainActor;
    private Time duration;
    private int age;

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

    private int getFilmId() {
        return film_id;
    }

    private String getTitle() {
        return title;
    }

    private String getGenre() {
        return genre;
    }

    private String getMainActor() {
        return mainActor;
    }

    private String getDirector() {
        return director;
    }

    private int getAge() {
        return age;
    }

    private Time getDuration() {
        return duration;
    }

    private void setFilm_id(int id) {
        this.film_id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }

    private void setDirector(String director) {
        this.director = director;
    }

    private void setDuration(Time duration) {
        this.duration = duration;
    }

    private void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    private void setAge(int age) {
        this.age = age;
    }
}
