package xyz.fmdb.fmdb.resources.review.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import xyz.fmdb.fmdb.common.response.Response;
import xyz.fmdb.fmdb.common.utils.JwtUtil;
import xyz.fmdb.fmdb.repositories.auth.boundary.AccountRepository;
import xyz.fmdb.fmdb.repositories.auth.entity.Account;
import xyz.fmdb.fmdb.repositories.movie.boundary.MovieRepository;
import xyz.fmdb.fmdb.repositories.movie.entity.Movie;
import xyz.fmdb.fmdb.repositories.review.boundary.ReviewRepository;
import xyz.fmdb.fmdb.repositories.review.entity.Review;
import xyz.fmdb.fmdb.resources.review.entity.CreateReviewRequest;
import xyz.fmdb.fmdb.resources.review.entity.DeleteReviewRequest;
import xyz.fmdb.fmdb.resources.review.entity.EditReviewRequest;
@Component
public class ReviewControl {

    JwtUtil jwtUtil;
    ReviewRepository reviewRepository;
    MovieRepository movieRepository;
    AccountRepository accountRepository;
    @Autowired
    public ReviewControl(JwtUtil jwtUtil, ReviewRepository reviewRepository, MovieRepository movieRepository, AccountRepository accountRepository) {
        this.jwtUtil = jwtUtil;
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity createReviewController(String authorization,CreateReviewRequest createReviewRequest) {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized", HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }
        Movie movie = movieRepository.findOneById(createReviewRequest.getMovieId());
        if(movie==null){
            return new ResponseEntity("Movie doesn't exist",HttpStatus.FORBIDDEN);
        }
        Review toBeSaved=new Review(createReviewRequest.getText(),createReviewRequest.getRating(),createReviewRequest.getMovieId(),accountId);
        reviewRepository.save(toBeSaved);
        return new ResponseEntity("Successful", HttpStatus.OK);
    }

    public ResponseEntity editReviewController(String authorization, EditReviewRequest editReviewRequest) {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        Review review = reviewRepository.findOneById(editReviewRequest.getId());
        if(review==null){
            return new ResponseEntity("Review doesn't exist",HttpStatus.FORBIDDEN);
        }
        if(editReviewRequest.getRating()!=0){
            review.setId(editReviewRequest.getId());
        }
        if(editReviewRequest.getText()!=null){
            review.setText(editReviewRequest.getText());
        }

        reviewRepository.save(review);
        return new ResponseEntity("Successful", HttpStatus.OK);
    }

    public ResponseEntity deleteReviewController(String authorization, DeleteReviewRequest deleteReviewRequest) {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        Long accountId= jwtUtil.getAccountIdFromJwtToken(authorization);
        Account account=accountRepository.findOneById(accountId);
        if(account==null){
            return new ResponseEntity("Account doesn't exist",HttpStatus.FORBIDDEN);
        }

        Review review = reviewRepository.findOneById(deleteReviewRequest.getId());
        if(review==null){
            return new ResponseEntity("Review doesn't exist",HttpStatus.FORBIDDEN);
        }


        reviewRepository.deleteById(deleteReviewRequest.getId());
        return new ResponseEntity("Successful", HttpStatus.OK);
    }

    public ResponseEntity reviewsController(String authorization) {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(new Response("Successful",reviewRepository.findAll()),HttpStatus.OK);
    }

    public ResponseEntity movieReviewsController(String authorization, Long movieId) {
        if(!jwtUtil.validateJwtToken(authorization)){
            return new ResponseEntity("Not authorized",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(new Response("Successful",reviewRepository.findByMovieId(movieId)),HttpStatus.OK);

    }
}
