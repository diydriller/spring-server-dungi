package com.dungi.core.integration.store.memo;

import com.dungi.core.domain.memo.query.MemoDetail;
import com.dungi.core.domain.memo.model.Memo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoStore {
    void saveMemo(Memo memo);

    List<MemoDetail> findAllMemo(Long userId, Long roomId);

    void updateMemo(Long userId, Long memoId, String memoItem, String memoColor);

    void moveMemo(Long userId, Long memoId, Double xPosition, Double yPosition);

    void deleteMemo(Long userId, Long memoId);
}
