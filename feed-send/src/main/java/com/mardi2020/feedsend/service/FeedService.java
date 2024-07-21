package com.mardi2020.feedsend.service;

import com.mardi2020.feedcommon.posting.message.NewPosting;
import com.mardi2020.feedcommon.posting.topic.PostingKafkaTopic;
import com.mardi2020.feedsend.Repository.FeedRepository;
import com.mardi2020.feedsend.client.FeedStoreServiceClient;
import com.mardi2020.feedsend.client.UserServiceClient;
import com.mardi2020.feedsend.client.dto.PostingInfoDTO;
import com.mardi2020.feedsend.model.Feed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("feedService")
public class FeedService {

    private static final int MAX_SIZE = 5;

    private final FeedRepository feedRepository;

    private final FeedStoreServiceClient feedStoreServiceClient;

    private final UserServiceClient userServiceClient;

    private final RedisTemplate<String, String> redisTemplate;

    private final CacheManager cacheManager;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = PostingKafkaTopic.NEW_POSTING, containerFactory = "feedKafkaListenerContainerFactory")
    public void receiveFriendPosting(final ConsumerRecord<String, NewPosting> consumerRecord) {
        final NewPosting newPosting = consumerRecord.value();
        final Long writerId = newPosting.userId();
        final Long postId = newPosting.postId();

        logger.info("[Kafka consume message] -> " + newPosting);
        ResponseEntity<List<Long>> userFollowingList = userServiceClient.getUserFollowingList(writerId);
        if (userFollowingList.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("following list를 가져오는데 실패했습니다. -> " + userFollowingList.getStatusCode());
        }
        if (userFollowingList.getBody() == null || userFollowingList.getBody().isEmpty()) {
            return;
        }
        logger.info("글을 작성한 사용자의 following list ->" + userFollowingList.getBody());

        Objects.requireNonNull(userFollowingList.getBody()).forEach(userId -> {
            addToQueue(postId.toString(), userId.toString());
            if (cacheManager.getCache("feedCache") != null) {
                cacheManager.getCache("feedCache").evict(userId.toString());
            }
            Optional<Feed> feed = feedRepository.findByUserId(userId.toString());
            List<Long> postings = feed.isPresent() ? feed.get().getPostings() : new ArrayList<>();
            postings.add(postId);
            feed.ifPresent(feedRepository::delete);
            feedRepository.save(Feed.builder()
                    .postings(postings)
                    .userId(userId.toString())
                    .build());
        });
    }

    @Cacheable(value = "feedCache", key="#userId", cacheManager = "cacheManager", condition = "#pageable.pageNumber == 0")
    public List<PostingInfoDTO> getFeedList(final String userId, final Pageable pageable) {
        final int pageNumber = pageable.getPageNumber();
        final Feed feed = feedRepository.findByUserId(userId).orElseThrow(RuntimeException::new);
        final int start = MAX_SIZE * pageNumber;
        List<Long> result = feed.getPostings();
        result.sort(Comparator.reverseOrder());
        result = result.subList(start, Math.min(start + MAX_SIZE, result.size()));
        ResponseEntity<List<PostingInfoDTO>> response = feedStoreServiceClient.getPostingInfoList(result);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("포스팅 정보를 불러오는데 실패했습니다." + response.getStatusCode());
        }
        return response.getBody();
    }

    private void addToQueue(final String postId, final String userId) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        if (listOps.size(userId) == MAX_SIZE) {
            listOps.leftPop(userId);
        }
        listOps.rightPush(userId, postId);
    }
}
