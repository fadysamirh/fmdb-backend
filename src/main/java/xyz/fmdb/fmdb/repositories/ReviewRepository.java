package xyz.fmdb.fmdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.fmdb.fmdb.models.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
