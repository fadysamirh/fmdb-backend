package xyz.fmdb.fmdb.models;

import org.springframework.web.multipart.MultipartFile;
import xyz.fmdb.fmdb.annotations.Image;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class MovieRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private String fileName;

//    @NotNull
    private Date releaseDate;

    @NotNull
    @Image
    private MultipartFile image;

    public MovieRequest(String name, String description, String fileName, Date releaseDate, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
        this.image = image;
    }
    public MovieRequest(String name, String description, String fileName, Date releaseDate) {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
    }
    public MovieRequest(String name, String description, Date releaseDate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
    }
    public MovieRequest() {

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}
