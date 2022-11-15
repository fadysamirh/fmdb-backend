package xyz.fmdb.fmdb.resources.review.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.fmdb.fmdb.resources.review.control.ReviewControl;
import xyz.fmdb.fmdb.resources.review.entity.CreateReviewRequest;
import xyz.fmdb.fmdb.resources.review.entity.DeleteReviewRequest;
import xyz.fmdb.fmdb.resources.review.entity.EditReviewRequest;

import javax.validation.Valid;
@RestController
@RequestMapping("/review")
public class ReviewResource {
    private ReviewControl reviewControl;

    @Autowired
    public ReviewResource(ReviewControl reviewControl) {
        this.reviewControl = reviewControl;
    }


    @RequestMapping(method = RequestMethod.POST,value = "/createReview")
    public ResponseEntity createReview(@RequestHeader("authorization") String authorization, @Valid @RequestBody CreateReviewRequest createReviewRequest){
        return reviewControl.createReviewController(authorization,createReviewRequest);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/editReview")
    public ResponseEntity editReview(@RequestHeader("authorization") String authorization, @RequestBody EditReviewRequest editReviewRequest){
        return  reviewControl.editReviewController(authorization,editReviewRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteReview")
    public ResponseEntity deleteReview(@RequestHeader("authorization") String authorization, @RequestBody DeleteReviewRequest deleteReviewRequest){
        return reviewControl.deleteReviewController(authorization,deleteReviewRequest);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/reviews")
    public ResponseEntity reviews(@RequestHeader("authorization") String authorization){
        return reviewControl.reviewsController(authorization);

    }
    @RequestMapping(method = RequestMethod.GET,value = "/movieReviews")
    public ResponseEntity movieReviews(@RequestHeader("authorization") String authorization, @RequestParam("movieId") Long movieId){
        return reviewControl.movieReviewsController(authorization,movieId);
    }
}
