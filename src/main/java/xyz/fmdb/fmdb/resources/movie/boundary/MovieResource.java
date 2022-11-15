package xyz.fmdb.fmdb.resources.movie.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.fmdb.fmdb.common.validations.Image;
import xyz.fmdb.fmdb.resources.movie.control.MovieControl;
import xyz.fmdb.fmdb.resources.movie.entity.DeleteMovieRequest;
import xyz.fmdb.fmdb.resources.movie.entity.EditMovieImageRequest;
import xyz.fmdb.fmdb.resources.movie.entity.EditMovieRequest;
import xyz.fmdb.fmdb.resources.movie.entity.CreateMovieRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;


@RestController
@RequestMapping("/movie")

public class MovieResource {
    private MovieControl movieControl;

    @Autowired
    public MovieResource(MovieControl movieControl) {
        this.movieControl = movieControl;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/createMovie" )
    public ResponseEntity createMovie(@RequestHeader("authorization") String authorization, @Valid @RequestBody CreateMovieRequest movieRequest) throws IOException {

        return movieControl.createMovieController(authorization,movieRequest);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/editMovieImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE  )
    public ResponseEntity editMovieImage(@RequestHeader("authorization") String authorization,@Valid @NotNull @RequestParam("imageData") MultipartFile imageData, @NotBlank @RequestParam("movieId") Long movieId) throws IOException {
        return movieControl.editMovieImageController(authorization,movieId,imageData);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/editMovie")
    public ResponseEntity editMovie(@RequestHeader("authorization") String authorization, @Valid @RequestBody EditMovieRequest editMovieRequest) throws IOException {
        return movieControl.editMovieController(authorization,editMovieRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteMovie")
    public ResponseEntity deleteMovie (@RequestHeader("authorization") String authorization,@Valid @RequestBody DeleteMovieRequest deleteMovieRequest) throws IOException {
        return movieControl.deleteMovieRequestController(authorization,deleteMovieRequest);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/movies")
    public ResponseEntity movies(@RequestHeader("authorization") String authorization){
        return movieControl.moviesController(authorization);
    }

    @RequestMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody ResponseEntity getImage(@RequestHeader("authorization") String authorization,@RequestParam("fileName") String fileName) throws IOException {
        return  movieControl.getImageController(authorization,fileName);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/uploadImage"  )
    public ResponseEntity uploadImage(@RequestHeader("authorization") String authorization, @NotNull @RequestParam("imageData") MultipartFile imageData, @NotNull @RequestParam("movieId") Long movieId, BindingResult bindingResult) throws IOException {
        return movieControl.uploadImageController(authorization,movieId,imageData);
    }

}
