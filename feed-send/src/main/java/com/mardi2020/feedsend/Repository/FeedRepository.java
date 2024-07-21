package com.mardi2020.feedsend.Repository;

import com.mardi2020.feedsend.model.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface FeedRepository extends MongoRepository<Feed, String> {

    Optional<Feed> findByUserId(String userId);
}
