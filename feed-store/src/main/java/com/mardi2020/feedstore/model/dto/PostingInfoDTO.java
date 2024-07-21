package com.mardi2020.feedstore.model.dto;

import java.util.List;

public record PostingInfoDTO(Long postId, Long userId, String text, List<String> imageUrl, String createdAt) {
}
