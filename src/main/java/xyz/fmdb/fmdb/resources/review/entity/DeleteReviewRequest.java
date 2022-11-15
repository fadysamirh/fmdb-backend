package xyz.fmdb.fmdb.resources.review.entity;

import javax.validation.constraints.NotNull;

public class DeleteReviewRequest {
    @NotNull(message = "Id must not be null")
    private Long id;
    public DeleteReviewRequest(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
