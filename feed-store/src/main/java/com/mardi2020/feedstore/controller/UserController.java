package com.mardi2020.feedstore.controller;

import com.mardi2020.feedstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getUserFollowingList(@PathVariable Long userId) {
        List<Long> userFollowingList = userService.findUserFollowingList(userId);
        return new ResponseEntity<>(userFollowingList, HttpStatus.OK);
    }
}
