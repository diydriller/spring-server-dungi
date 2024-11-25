package com.dungi.core.domain.memo.service;

import com.dungi.core.domain.memo.dto.GetMemoDto;
import com.dungi.core.domain.memo.model.Memo;
import com.dungi.core.integration.store.memo.MemoStore;
import com.dungi.core.integration.store.room.RoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {
    private final MemoStore memoStore;
    private final RoomStore roomStore;

    // 메모 생성 기능
    // 유저가 방에 입장해있는지 확인 - 메모 생성
    // 메모 생성시 캐시 삭제
    @Transactional
    @CacheEvict(key = "#roomId", value = "getMemo")
    public void createMemo(String memoItem,
                           String memoColor,
                           Double xPosition,
                           Double yPosition,
                           Long userId,
                           Long roomId
    ) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        var memo = Memo.builder()
                .userId(userId)
                .roomId(roomId)
                .memoItem(memoItem)
                .xPosition(xPosition)
                .yPosition(yPosition)
                .memoColor(memoColor)
                .build();
        memoStore.saveMemo(memo);
    }

    // 메모 조회 기능
    // 유저가 방에 입장해있는지 검증 - 메모 조회
    // 캐시 사용해서 조회
    @Transactional(readOnly = true)
    @Cacheable(key = "#roomId", value = "getMemo")
    public List<GetMemoDto> getMemo(Long roomId, Long userId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        return memoStore.findAllMemo(userId, roomId);
    }

    // 메모 수정 기능
    // 유저가 방에 입장해있는지 검증 - 메모 수정
    // 메모 수정시 캐시 삭제
    @Transactional
    @CacheEvict(key = "#roomId", value = "getMemo")
    public void updateMemo(String memoItem, String memoColor, Long userId, Long roomId, Long memoId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        memoStore.updateMemo(userId, memoId, memoItem, memoColor);
    }

    // 메모 이동 기능
    // 유저가 방에 입장해있는지 검증 - 메모 이동
    // 메모 이동시 캐시 삭제
    @Transactional
    @CacheEvict(key = "#roomId", value = "getMemo")
    public void moveMemo(Double xPosition, Double yPosition, Long userId, Long roomId, Long memoId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        memoStore.moveMemo(userId, memoId, xPosition, yPosition);
    }

    // 메모 삭제 기능
    // 유저가 방에 입장해있는지 검증 - 메모 삭제
    // 메모 삭제시 캐시 삭제
    @Transactional
    @CacheEvict(key = "#roomId", value = "getMemo")
    public void deleteMemo(Long userId, Long roomId, Long memoId) {
        roomStore.getRoomEnteredByUser(userId, roomId);
        memoStore.deleteMemo(userId, memoId);
    }
}
