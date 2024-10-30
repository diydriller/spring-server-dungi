package com.dungi.jpa.repository;

import com.dungi.core.domain.vote.model.UserVoteItem;
import com.dungi.core.domain.vote.model.Vote;
import com.dungi.core.domain.vote.model.VoteItem;
import com.dungi.jpa.repository.vote.UserVoteItemJpaRepository;
import com.dungi.jpa.repository.vote.VoteItemJpaRepository;
import com.dungi.jpa.repository.vote.VoteJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DataJpaTest
public class VoteJpaRepositoryTest {

    @Autowired
    private UserVoteItemJpaRepository userVoteItemJpaRepository;

    @Autowired
    private VoteItemJpaRepository voteItemJpaRepository;

    @Autowired
    private VoteJpaRepository voteJpaRepository;

    @Autowired
    private TransactionTemplate transaction;

    @Test
    @DisplayName("멤버의 투표 선택이 중복으로 저장되는 문제")
    void userVoteChoiceNotUniqueTest() throws InterruptedException {

        // given
        int threadNum = 5;
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        CountDownLatch latch = new CountDownLatch(threadNum);

        transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        var savedVoteItem = transaction.execute((status -> {
            var vote = Vote.builder()
                    .roomId(1L)
                    .userId(1L)
                    .title("투표")
                    .build();
            voteJpaRepository.save(vote);

            var voteItem = new VoteItem("선택1");
            voteItem.setVote(vote);
            return voteItemJpaRepository.save(voteItem);
        }));

        // when
        List<Exception> exceptionList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            service.execute(() -> {
                try {
                    transaction.execute((status -> {
                        createUserVoteItem(1L, savedVoteItem);
                        return null;
                    }));
                } catch (Exception e) {
                    e.printStackTrace();
                    exceptionList.add(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        service.shutdown();
        latch.await();

        // then
        Assertions.assertTrue(exceptionList.size() != 0);
        for (Exception e : exceptionList) {
            Assertions.assertTrue(e instanceof DataIntegrityViolationException);
        }
    }

    private void createUserVoteItem(Long userId, VoteItem voteItem) {
        userVoteItemJpaRepository.findByUserAndVoteItem(userId, voteItem)
                .ifPresentOrElse(
                        (uvi) -> {
                            uvi.changeChoice();
                            userVoteItemJpaRepository.save(uvi);
                        }, () -> {
                            var userVoteItem = new UserVoteItem(userId, voteItem);
                            userVoteItemJpaRepository.save(userVoteItem);
                        });
    }
}
