package com.mardi2020.feedstore.repository;

import com.mardi2020.feedstore.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingMySQLRepository extends JpaRepository<Posting, Long> {

    List<Posting> findByIdIn(List<Long> postIdList);
}
