package com.project.dungi.infrastructure.store.memo;

import com.project.dungi.common.exception.BaseException;
import com.project.dungi.domain.common.DeleteStatus;
import com.project.dungi.domain.memo.model.Memo;
import com.project.dungi.domain.memo.service.MemoStore;
import com.project.dungi.domain.memo.dto.GetMemoDto;
import com.project.dungi.infrastructure.jpa.memo.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.dungi.common.response.BaseResponseStatus.AUTHORIZATION_ERROR;
import static com.project.dungi.common.response.BaseResponseStatus.NOT_EXIST_MEMO;


@Component
@RequiredArgsConstructor
public class MemoStoreImpl implements MemoStore {

    private final MemoRepository memoRepository;

    @Override
    public void saveMemo(Memo memo) {
        memoRepository.save(memo);
    }

    @Override
    public List<GetMemoDto> findAllMemo(Long userId, Long roomId) {
        return memoRepository.findAllMemoInRoom(roomId, DeleteStatus.NOT_DELETED);
    }

    @Override
    public void updateMemo(Long userId, Long memoId, String memoItem, String memoColor) {
        memoRepository.findMemoById(memoId, DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m)->{
                            if(!m.getUserId().equals(userId)){
                                throw new BaseException(AUTHORIZATION_ERROR);
                            }
                            m.changeColor(memoColor);
                            m.changeItem(memoItem);
                            memoRepository.save(m);
                        },
                        ()->{
                            throw new BaseException(NOT_EXIST_MEMO);
                        }
                );
    }

    @Override
    public void moveMemo(Long userId, Long memoId, Double xPosition, Double yPosition) {
        memoRepository.findMemoById(memoId,DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m)->{
                            m.move(xPosition, yPosition);
                            memoRepository.save(m);
                        },
                        ()->{
                            throw new BaseException(NOT_EXIST_MEMO);
                        }
                );
    }

    @Override
    public void deleteMemo(Long userId, Long memoId) {
        memoRepository.findMemoById(memoId,DeleteStatus.NOT_DELETED)
                .ifPresentOrElse(
                        (m)->{
                            m.deactivate();
                            memoRepository.save(m);
                        },
                        ()->{
                            throw new BaseException(NOT_EXIST_MEMO);
                        }
                );
    }
}
