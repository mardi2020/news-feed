package com.mardi2020.feedsend.client;

import com.mardi2020.feedsend.client.dto.PostingInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "feed-store-posting", url = "http://localhost:8000/feed-store")
public interface FeedStoreServiceClient {

    @GetMapping("/{postingIdList}")
    ResponseEntity<List<PostingInfoDTO>> getPostingInfoList(@PathVariable List<Long> postingIdList);
}
