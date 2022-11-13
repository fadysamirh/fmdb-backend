package xyz.fmdb.fmdb.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name="movie")
public class Movie {
    //columns
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @Column(name="description")
    private String description;
    @NotBlank
    @Column(name = "fileName")
    private String fileName;

    @NotNull
    @Column(name = "releaseDate")
    private Date releaseDate;

    //constructors
    public Movie(long id, String name, String description,  Date releaseDate) {
        this.id = id;
        this.name = name;
        this.description = description;

        this.releaseDate = releaseDate;
    }
    public Movie( String name, String description, Date releaseDate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public Movie(String name, String description,String fileName, Date releaseDate ) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.fileName=fileName;
    }

    public Movie(){

    }
    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
