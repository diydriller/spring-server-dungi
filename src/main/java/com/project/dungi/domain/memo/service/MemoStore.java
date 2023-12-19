package com.project.dungi.domain.memo.service;

import com.project.dungi.domain.memo.model.Memo;
import com.project.dungi.domain.memo.dto.GetMemoDto;

import java.util.List;

public interface MemoStore {
    void saveMemo(Memo memo);
    List<GetMemoDto> findAllMemo(Long userId, Long roomId);
    void updateMemo(Long userId , Long memoId , String memoItem, String memoColor);
    void moveMemo(Long userId, Long memoId , Double xPosition, Double yPosition);
    void deleteMemo(Long userId, Long memoId);
}
