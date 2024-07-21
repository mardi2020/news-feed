package com.mardi2020.feedstore.service;

import com.mardi2020.feedstore.model.dto.InsertPostingDTO;
import com.mardi2020.feedstore.model.dto.PostingInfoDTO;

import java.util.List;

public interface PostingService {

    Long add(InsertPostingDTO insertPostingDTO);

    List<PostingInfoDTO> getPostings(List<Long> postIdList);
}
