package com.feedback_api.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByTitle(String title);

    // JpaRepository’s methods: save(), findOne(), findById(), findAll(), count(), delete(), deleteById()
}
