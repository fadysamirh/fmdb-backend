package xyz.fmdb.fmdb.models;

import xyz.fmdb.fmdb.annotations.Password;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    @NotBlank(message = "username is mandatory")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "password is mandatory")
    @Password
    private String password;
    //constructor

    public Account(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account() {

    }
    //getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
