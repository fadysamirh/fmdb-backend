package xyz.fmdb.fmdb.repositories.movie.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.fmdb.fmdb.repositories.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findOneByFileName (String fileName);
    Movie findOneById (Long id);

}
