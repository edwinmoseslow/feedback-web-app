package com.feedback_api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/api")
public class FeedbackController {
    @Autowired
    FeedbackRepository feedbackRepository;

    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@RequestParam(required = false) String title) {
        try {
            List<Feedback> feedbacks = new ArrayList<>();
            if(title == null) {
                feedbackRepository.findAll().forEach(feedbacks :: add);
            } else {
                feedbackRepository.findByTitle(title).forEach(feedbacks :: add);
            }

            if(feedbacks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") long id) {
        try {
            Optional<Feedback> feedback = feedbackRepository.findById(id);

            if (feedback.isPresent()) {
                return new ResponseEntity<>(feedback.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/feedbacks")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        // try {
            Feedback _feedback = feedbackRepository
                    .save(new Feedback(feedback.getTitle(), feedback.getDescription(), feedback.getRating()));
            return new ResponseEntity<>(_feedback, HttpStatus.CREATED);
        // } catch (Exception ex) {
            // return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        // }
    }

    @PutMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable("id") long id, @RequestBody Feedback feedback) {
        try {
            Optional<Feedback> feedbackData = feedbackRepository.findById(id);

            if (feedbackData.isPresent()) {
                Feedback _feedback = feedbackData.get();
                _feedback.setTitle(feedback.getTitle());
                _feedback.setDescription(feedback.getDescription());
                _feedback.setRating(feedback.getRating());
                return new ResponseEntity<>(feedbackRepository.save(_feedback), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable("id") long id) {
        try {
            feedbackRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/feedbacks")
    public ResponseEntity<HttpStatus> deleteAllFeedbacks() {
        try {
            feedbackRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
