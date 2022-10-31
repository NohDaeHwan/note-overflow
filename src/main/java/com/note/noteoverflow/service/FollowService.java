package com.note.noteoverflow.service;

import com.note.noteoverflow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {

    private final FollowRepository followRepository;

    public int followingList(Long userId) {
        int following = followRepository.findByFollowId(userId).size();
        return following;
    }
}
