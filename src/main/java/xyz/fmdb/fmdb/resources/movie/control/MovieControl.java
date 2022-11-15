package xyz.fmdb.fmdb.resources.movie.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.fmdb.fmdb.common.response.Response;
import xyz.fmdb.fmdb.common.utils.FileUtil;
import xyz.fmdb.fmdb.common.utils.JwtUtil;
import xyz.fmdb.fmdb.repositories.auth.boundary.AccountRepository;
import xyz.fmdb.fmdb.repositories.auth.entity.Account;
import xyz.fmdb.fmdb.repositories.movie.boundary.MovieRepository;
import xyz.fmdb.fmdb.repositories.movie.entity.Movie;
import xyz.fmdb.fmdb.resources.movie.entity.EditMovieImageRequest;
import xyz.fmdb.fmdb.resources.movie.entity.EditMovieRequest;
import xyz.fmdb.fmdb.resources.movie.entity.CreateMovieRequest;
import xyz.fmdb.fmdb.resources.movie.entity.DeleteMovieRequest;

import java.io.File;
import java.io.IOException;
@Component
public class MovieControl {

    JwtUtil jwtUtil;
    private MovieRepository movieRepository;
    private AccountRepository accountRepository;
    @Autowired
    public MovieControl(JwtUtil jwtUtil, MovieRepository movieRepository,AccountRepository accountRepository) {
        this.jwtUtil = jwtUtil;
        this.movieRepository = movieRepository;
        this.accountRepository=accountRepository;
    }




    public ResponseEntity createMovieController(String authorization, CreateMovieRequest movieRequest) throws IOException {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        Movie toBeSaved=new Movie(movieRequest.getName(),movieRequest.getDescription(),"",movieRequest.getReleaseDate());
        Movie saved=movieRepository.save(toBeSaved);
        return new ResponseEntity(new Response("Successful",saved),HttpStatus.OK);
    }


    public ResponseEntity editMovieImageController(String authorization,Long movieId ,MultipartFile imageData) throws IOException {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        Movie foundMovieById=movieRepository.findOneById(movieId);
        if(foundMovieById==null){
            return new ResponseEntity("Movie doesn't exist",HttpStatus.FORBIDDEN);
        }

        String fileName = StringUtils.cleanPath(imageData.getOriginalFilename());

        Movie foundByFileNameMovie=movieRepository.findOneByFileName(fileName);
        if(foundByFileNameMovie!=null){
            return new ResponseEntity("File already exists",HttpStatus.FORBIDDEN);
        }
        String uploadDir = "src/main/resources/uploads";
        FileUtil.deleteFile(uploadDir,foundMovieById.getFileName());

        FileUtil.saveFile(uploadDir, fileName, imageData);
        foundMovieById.setFileName(fileName);
        movieRepository.save(foundMovieById);

        return new ResponseEntity("Successful",HttpStatus.OK);
    }

    public ResponseEntity editMovieController(String authorization, EditMovieRequest editMovieRequest) {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        Long movieId=editMovieRequest.getId();
        Movie foundMovieById=movieRepository.findOneById(movieId);
        if(foundMovieById==null){
            return new ResponseEntity("Movie doesn't exist",HttpStatus.FORBIDDEN);
        }


        if(editMovieRequest.getName()!=null){
            foundMovieById.setName(editMovieRequest.getName());
        }
        if(editMovieRequest.getDescription()!=null){
            foundMovieById.setDescription(editMovieRequest.getDescription());
        }

        if(editMovieRequest.getReleaseDate()!=null){
            foundMovieById.setReleaseDate(editMovieRequest.getReleaseDate());

        }
        movieRepository.save(foundMovieById);

        return new ResponseEntity("Successful",HttpStatus.OK);
    }

    public ResponseEntity deleteMovieRequestController(String authorization, DeleteMovieRequest deleteMovieRequest) throws IOException {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        Movie foundMovieById=movieRepository.findOneById(deleteMovieRequest.getId());
        if(foundMovieById==null){
            return new ResponseEntity("Movie doesn't exist",HttpStatus.FORBIDDEN);
        }
        String deleteDir="src/main/resources/uploads/";
        FileUtil.deleteFile(deleteDir,foundMovieById.getFileName());

        movieRepository.deleteById(deleteMovieRequest.getId());

        return new ResponseEntity("Successful",HttpStatus.OK);
    }

    public ResponseEntity moviesController(String authorization){
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(new Response("Successful",movieRepository.findAll()),HttpStatus.OK);
    }
    public ResponseEntity getImageController(String authorization,String fileName) throws IOException {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        var imgFile = new ClassPathResource("uploads/"+fileName);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }
    public ResponseEntity uploadImageController (String authorization, Long movieId ,MultipartFile imageData) throws IOException {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Movie foundMovieById=movieRepository.findOneById(movieId);
        if(foundMovieById==null){
            return new ResponseEntity("Movie doesn't exist",HttpStatus.FORBIDDEN);
        }
        String fileName = StringUtils.cleanPath(imageData.getOriginalFilename());
        Movie foundMovie=movieRepository.findOneByFileName(fileName);
        if(foundMovie!=null){
            return new ResponseEntity("File already exists",HttpStatus.FORBIDDEN);
        }
        String uploadDir = "src/main/resources/uploads";

        FileUtil.saveFile(uploadDir, fileName, imageData);
        foundMovieById.setFileName(fileName);
        movieRepository.save(foundMovieById);
        return new ResponseEntity("Successful",HttpStatus.OK);
    }
}
