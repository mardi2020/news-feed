package com.mardi2020.feedstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Posting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @ElementCollection
    @CollectionTable(name = "posting_images", joinColumns = @JoinColumn(name = "posting_id"))
    @Size(max = 5, message = "A post can have a maximum of 5 photos.")
    private List<PostingImage> images;

    public List<String> onlyImageUrls() {
        return images.stream()
                .map(PostingImage::getUrl)
                .collect(Collectors.toList());
    }
}
