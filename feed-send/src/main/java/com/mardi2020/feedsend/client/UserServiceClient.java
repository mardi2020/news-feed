package com.mardi2020.feedsend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "feed-store-user", url = "http://localhost:8000/feed-store")
public interface UserServiceClient {

    @GetMapping("/user/following/{userId}")
    ResponseEntity<List<Long>> getUserFollowingList(@PathVariable Long userId);
}
