package com.dungi.core.infrastructure.store.memo;

import com.dungi.core.domain.memo.dto.GetMemoDto;
import com.dungi.core.domain.memo.model.Memo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoStore {
    void saveMemo(Memo memo);

    List<GetMemoDto> findAllMemo(Long userId, Long roomId);

    void updateMemo(Long userId, Long memoId, String memoItem, String memoColor);

    void moveMemo(Long userId, Long memoId, Double xPosition, Double yPosition);

    void deleteMemo(Long userId, Long memoId);
}
