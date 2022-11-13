package xyz.fmdb.fmdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.fmdb.fmdb.models.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findOneByFileName (String fileName);
}
