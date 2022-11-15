package xyz.fmdb.fmdb.resources.movie.entity;

import org.springframework.web.multipart.MultipartFile;
import xyz.fmdb.fmdb.common.validations.Image;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditMovieImageRequest {
    @NotNull
    private long movieId;
    @Image
    @NotNull
    private MultipartFile image;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
