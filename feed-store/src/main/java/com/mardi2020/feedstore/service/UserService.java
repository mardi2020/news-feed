package com.mardi2020.feedstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional(readOnly = true)
    public List<Long> findUserFollowingList(final Long userId) {
        return List.of(10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L, 21L, 23L, 24L, 25L, 30L);
    }
}
