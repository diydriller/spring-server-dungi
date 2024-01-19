package com.dungi.core.domain.memo.service;

import com.dungi.core.domain.memo.dto.GetMemoDto;
import com.dungi.core.domain.memo.model.Memo;

import java.util.List;

public interface MemoStore {
    void saveMemo(Memo memo);
    List<GetMemoDto> findAllMemo(Long userId, Long roomId);
    void updateMemo(Long userId , Long memoId , String memoItem, String memoColor);
    void moveMemo(Long userId, Long memoId , Double xPosition, Double yPosition);
    void deleteMemo(Long userId, Long memoId);
}
