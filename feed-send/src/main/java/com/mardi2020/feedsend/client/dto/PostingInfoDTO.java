package com.mardi2020.feedsend.client.dto;

import java.util.List;

public record PostingInfoDTO(Long postId, Long userId, String text, List<String> imageUrl, String createdAt) {
}
