package com.mardi2020.feedstore.controller;


import com.mardi2020.feedstore.model.dto.InsertPostingDTO;
import com.mardi2020.feedstore.model.dto.PostingInfoDTO;
import com.mardi2020.feedstore.service.PostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @PostMapping(value = "/posting")
    ResponseEntity<Long> add(@Valid @RequestBody InsertPostingDTO insertPostingDTO) {
        Long postId = postingService.add(insertPostingDTO);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{postingIdList}")
    ResponseEntity<List<PostingInfoDTO>> getPostingInfoList(@PathVariable List<Long> postingIdList) {
        List<PostingInfoDTO> postings = postingService.getPostings(postingIdList);
        return new ResponseEntity<>(postings, HttpStatus.OK);
    }
}
