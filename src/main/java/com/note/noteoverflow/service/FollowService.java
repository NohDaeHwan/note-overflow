package com.note.noteoverflow.service;

import com.note.noteoverflow.dto.FollowDto;
import com.note.noteoverflow.dto.FollowingDto;
import com.note.noteoverflow.repository.FollowRepository;
import com.note.noteoverflow.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {

    private final FollowRepository followRepository;

    private final UserAccountRepository userAccountRepository;

    @Transactional
    public List<FollowingDto> followingList(Long userId) {
        List<Long> followingId = followRepository.findByFollowId(userId);
        return userAccountRepository.findByFollowId(followingId);
    }

}
