package com.dungi.jpa.store.memo;

import com.dungi.common.exception.BaseException;
import com.dungi.common.response.BaseResponseStatus;
import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.memo.dto.GetMemoDto;
import com.dungi.core.domain.memo.model.Memo;
import com.dungi.core.infrastructure.store.memo.MemoStore;
import com.dungi.jpa.repository.memo.MemoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemoStoreImpl implements MemoStore {
    private final MemoJpaRepository memoJpaRepository;

    @Override
    public void saveMemo(Memo memo) {
        memoJpaRepository.save(memo);
    }

    @Override
    public List<GetMemoDto> findAllMemo(Long userId, Long roomId) {
        return memoJpaRepository.findAllMemoInRoom(roomId, DeleteStatus.NOT_DELETED);
    }

    @Override
    public void updateMemo(Long userId, Long memoId, String memoItem, String memoColor) {
        memoJpaRepository.findMemoById(memoId, DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m) -> {
                            if (!m.getUserId().equals(userId)) {
                                throw new BaseException(BaseResponseStatus.AUTHORIZATION_ERROR);
                            }
                            m.changeColor(memoColor);
                            m.changeItem(memoItem);
                            memoJpaRepository.save(m);
                        },
                        () -> {
                            throw new BaseException(BaseResponseStatus.NOT_EXIST_MEMO);
                        }
                );
    }

    @Override
    public void moveMemo(Long userId, Long memoId, Double xPosition, Double yPosition) {
        memoJpaRepository.findMemoById(memoId, DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m) -> {
                            m.move(xPosition, yPosition);
                            memoJpaRepository.save(m);
                        },
                        () -> {
                            throw new BaseException(BaseResponseStatus.NOT_EXIST_MEMO);
                        }
                );
    }

    @Override
    public void deleteMemo(Long userId, Long memoId) {
        memoJpaRepository.findMemoById(memoId, DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m) -> {
                            m.deactivate();
                            memoJpaRepository.save(m);
                        },
                        () -> {
                            throw new BaseException(BaseResponseStatus.NOT_EXIST_MEMO);
                        }
                );
    }
}
