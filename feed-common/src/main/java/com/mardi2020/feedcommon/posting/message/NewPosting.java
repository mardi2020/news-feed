package com.mardi2020.feedcommon.posting.message;


import java.util.List;

public record NewPosting(Long postId, Long userId, String body, List<String> imageUrls, String createdAt) {

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Long postId;
        private Long userId;
        private String body;
        private List<String> imageUrls;

        private String createdAt;

        public NewPosting build() {
            return new NewPosting(postId, userId, body, imageUrls, createdAt);
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder imageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
            return this;
        }
    }
}
