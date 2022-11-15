package xyz.fmdb.fmdb.resources.movie.entity;

import javax.validation.constraints.NotNull;

public class DeleteMovieRequest {
    @NotNull(message = "Id must not be null")
    private Long id;
    public DeleteMovieRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
