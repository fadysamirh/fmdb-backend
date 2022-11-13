package xyz.fmdb.fmdb.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "review")
public class Review {
    //columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="text")
    private String text;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

    public Review(String text, int rating, Long movie_id, Long account_id) {
        this.text = text;
        this.rating = rating;
        this.movie = new Movie(movie_id,"", "",new Date(1,1,2022));
        this.account = new Account(account_id,"","");
    }

    public Review() {

    }

    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
