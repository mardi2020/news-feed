package com.mardi2020.feedstore.model.dto;

import com.mardi2020.feedstore.model.Posting;
import com.mardi2020.feedstore.model.PostingImage;

import java.util.List;
import java.util.stream.Collectors;

public record InsertPostingDTO(String body, List<String> imageUrls, Long userId) {

    public Posting toPosting() {
        return Posting.builder()
                .userId(userId)
                .body(body)
                .images(imageUrls.stream().map((imageUrl) ->
                        PostingImage.builder()
                                .userId(userId)
                                .url(imageUrl)
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
