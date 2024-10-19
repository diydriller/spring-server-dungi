package com.dungi.core.domain.memo.service;

import com.dungi.core.domain.memo.dto.GetMemoDto;

import java.util.List;

public interface MemoService {
    void createMemo(String memoItem,
                    String memoColor,
                    Double xPosition,
                    Double yPosition,
                    Long userId,
                    Long roomId) throws Exception;

    List<GetMemoDto> getMemo(Long roomId, Long userId) throws Exception;

    void updateMemo(String memoItem, String memoColor, Long userId, Long roomId, Long memoId);

    void moveMemo(Double xPosition, Double yPosition, Long userId, Long roomId, Long memoId);

    void deleteMemo(Long userId, Long roomId, Long memoId);
}
