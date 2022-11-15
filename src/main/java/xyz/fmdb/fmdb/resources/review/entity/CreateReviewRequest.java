package xyz.fmdb.fmdb.resources.review.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateReviewRequest {
        @NotBlank(message = "text must not be blank")
        private String text;

        @NotNull(message = "rating must not be null")
        @Min(value = 1,message = "Rating must be at least 1")
        @Max(value=10,message = "Rating must be at most 10")
        private int rating;

        @NotNull(message = "Movie id must not be null")
        private Long movieId;

        public CreateReviewRequest() {

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

        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

}
