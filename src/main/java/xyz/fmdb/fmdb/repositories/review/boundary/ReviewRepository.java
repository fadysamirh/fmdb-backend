package xyz.fmdb.fmdb.repositories.review.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.fmdb.fmdb.repositories.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByMovieId(Long movieId);
    Review findOneById(Long id);
}
