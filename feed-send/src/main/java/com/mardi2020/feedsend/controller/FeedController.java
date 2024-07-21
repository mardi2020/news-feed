package com.mardi2020.feedsend.controller;

import com.mardi2020.feedsend.client.dto.PostingInfoDTO;
import com.mardi2020.feedsend.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final FeedService feedService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<PostingInfoDTO>> getUserFeed(@PathVariable String userId,
                                                  @PageableDefault(page=0, size=5, sort="createdDate", direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        logger.info("pageable data ->" + pageable);
        final List<PostingInfoDTO> postingList = feedService.getFeedList(userId, pageable);
        return new ResponseEntity<>(postingList, HttpStatus.OK);
    }
}
