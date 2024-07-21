package com.mardi2020.feedstore.service;

import com.mardi2020.feedcommon.posting.message.NewPosting;
import com.mardi2020.feedcommon.posting.topic.PostingKafkaTopic;
import com.mardi2020.feedstore.model.Posting;
import com.mardi2020.feedstore.model.dto.InsertPostingDTO;
import com.mardi2020.feedstore.model.dto.PostingInfoDTO;
import com.mardi2020.feedstore.repository.PostingMySQLRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostingServiceImpl implements PostingService {


    private final PostingMySQLRepository postingMySQLRepository;

    private final KafkaTemplate<String, NewPosting> kafkaTemplate;

    @Override
    @Transactional
    public Long add(InsertPostingDTO insertPostingDTO) {
        Posting savedEntity = postingMySQLRepository.save(insertPostingDTO.toPosting());

        kafkaTemplate.send(PostingKafkaTopic.NEW_POSTING,
                NewPosting.builder()
                        .postId(savedEntity.getId())
                        .body(savedEntity.getBody())
                        .userId(savedEntity.getUserId())
                        .imageUrls(savedEntity.onlyImageUrls())
                        .createdAt(savedEntity.getDateCreated().toString())
                        .build());
        return savedEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostingInfoDTO> getPostings(List<Long> postIdList) {
        List<Posting> postingList = postingMySQLRepository.findByIdIn(postIdList);
        return postingList.stream()
                .map(posting -> new PostingInfoDTO(posting.getId(),
                        posting.getUserId(),
                        posting.getBody(),
                        posting.onlyImageUrls(),
                        posting.getDateCreated().toString()))
                .collect(Collectors.toList());
    }
}
