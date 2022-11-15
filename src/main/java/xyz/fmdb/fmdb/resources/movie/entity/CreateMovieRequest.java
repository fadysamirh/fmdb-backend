package xyz.fmdb.fmdb.resources.movie.entity;

import org.springframework.web.multipart.MultipartFile;
import xyz.fmdb.fmdb.common.validations.Image;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class CreateMovieRequest {
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Description must not be blank")
    private String description;

    private String fileName;

    @NotNull(message = "Release date must not be null")
    private Date releaseDate;



    public CreateMovieRequest() {

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


}
