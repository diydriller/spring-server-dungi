package com.dungi.core.infrastructure.store.memo;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.memo.dto.GetMemoDto;
import com.dungi.core.domain.memo.model.Memo;
import com.dungi.core.domain.memo.service.MemoStore;
import com.dungi.core.infrastructure.jpa.memo.MemoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dungi.common.response.BaseResponseStatus.AUTHORIZATION_ERROR;
import static com.dungi.common.response.BaseResponseStatus.NOT_EXIST_MEMO;

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
                        (m)->{
                            if(!m.getUserId().equals(userId)){
                                throw new BaseException(AUTHORIZATION_ERROR);
                            }
                            m.changeColor(memoColor);
                            m.changeItem(memoItem);
                            memoJpaRepository.save(m);
                        },
                        ()->{
                            throw new BaseException(NOT_EXIST_MEMO);
                        }
                );
    }

    @Override
    public void moveMemo(Long userId, Long memoId, Double xPosition, Double yPosition) {
        memoJpaRepository.findMemoById(memoId,DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m)->{
                            m.move(xPosition, yPosition);
                            memoJpaRepository.save(m);
                        },
                        ()->{
                            throw new BaseException(NOT_EXIST_MEMO);
                        }
                );
    }

    @Override
    public void deleteMemo(Long userId, Long memoId) {
        memoJpaRepository.findMemoById(memoId,DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m)->{
                            m.deactivate();
                            memoJpaRepository.save(m);
                        },
                        ()->{
                            throw new BaseException(NOT_EXIST_MEMO);
                        }
                );
    }
}
