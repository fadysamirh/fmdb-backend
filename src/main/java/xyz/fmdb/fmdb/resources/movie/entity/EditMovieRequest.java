package xyz.fmdb.fmdb.resources.movie.entity;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class EditMovieRequest {
    @NotNull(message = "Id must not be null")
    private Long id;
    private String name;
    private String description;

    private String fileName;

    private Date releaseDate;


    public EditMovieRequest() {

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


    public long getId() {
        return this.id;
    }
    public String toString(){
        return ""+id;
    }
}
