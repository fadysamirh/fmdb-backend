package xyz.fmdb.fmdb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import xyz.fmdb.fmdb.models.ModelTO;
import xyz.fmdb.fmdb.models.Movie;
import xyz.fmdb.fmdb.models.MovieRequest;
import xyz.fmdb.fmdb.repositories.MovieRepository;
import xyz.fmdb.fmdb.response.Response;
import xyz.fmdb.fmdb.util.FileUploadUtil;
import xyz.fmdb.fmdb.util.JwtUtils;

import javax.validation.Valid;
import java.io.IOException;


@RestController
public class MovieController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private MovieRepository movieRepository;
    @RequestMapping(method = RequestMethod.GET,value = "/movies")

    public ResponseEntity movies(@RequestHeader("authorization") String authorization){
        if(!jwtUtils.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }

       return new ResponseEntity(new Response("Successful",movieRepository.findAll()),HttpStatus.OK);

    }
    @RequestMapping(method = RequestMethod.POST,value = "/createMovie",consumes = MediaType.MULTIPART_FORM_DATA_VALUE  )

    public ResponseEntity createMovie(@Valid @ModelAttribute MovieRequest movie) throws IOException {
        String fileName = StringUtils.cleanPath(movie.getImage().getOriginalFilename());

        Movie foundMovie=movieRepository.findOneByFileName(fileName);
        if(foundMovie!=null){
            return new ResponseEntity("File already exists",HttpStatus.FORBIDDEN);
        }
        String uploadDir = "/uploads";
        FileUploadUtil.saveFile(uploadDir, fileName, movie.getImage());
        Movie toBeSaved=new Movie(movie.getName(),movie.getDescription(),fileName,movie.getReleaseDate());
        movieRepository.save(toBeSaved);
        return new ResponseEntity("Successful",HttpStatus.OK);

    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMoviesException(MethodArgumentNotValidException e) {
        System.out.println("Here");
        //Returning password error message as a response.
        return new ResponseEntity<String>(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()),HttpStatus.BAD_REQUEST);
    }
}
