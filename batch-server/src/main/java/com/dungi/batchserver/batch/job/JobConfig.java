package com.dungi.batchserver.batch.job;

import com.dungi.common.exception.BaseException;
import com.dungi.core.domain.common.DeleteStatus;
import com.dungi.core.domain.room.model.Room;
import com.dungi.core.domain.todo.service.TodoService;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.infrastructure.store.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import static com.dungi.common.response.BaseResponseStatus.NOT_EXIST_BEST_MATE;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory job;
    private final EntityManagerFactory entityManagerFactory;
    private final TodoService todoService;
    private final UserStore userStore;
    private final StepBuilderFactory steps;
    private final int chunkSize = 10;

    @Bean
    public Step decideBestMemberStep() {
        return steps.get("decideBestMemberStep")
                .<Room, User>chunk(chunkSize)
                .reader(decideBestMemberPagingReader())
                .processor(decideBestMemberProcessor())
                .writer(decideBestMemberWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Room> decideBestMemberPagingReader() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("deleteStatus", DeleteStatus.NOT_DELETED);
        return new JpaPagingItemReaderBuilder<Room>()
                .queryString("SELECT r FROM Room r WHERE r.deleteStatus=:deleteStatus")
                .pageSize(chunkSize)
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(parameterValues)
                .name("decideBestMemberPagingReader")
                .build();
    }

    @Bean
    public ItemProcessor<Room, User> decideBestMemberProcessor() {
        return room -> {
            var bestMemberIdList = todoService.findBestMember(room.getId());
            if (bestMemberIdList.isEmpty()) {
                throw new BaseException(NOT_EXIST_BEST_MATE);
            }
            var bestMember = userStore.findUserById(bestMemberIdList.get(0));
            bestMember.increaseBestMateCount();
            return bestMember;
        };
    }

    @Bean
    public JpaItemWriter<User> decideBestMemberWriter() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean(name = "decideBestMateJob")
    public Job decideBestMemberJob() {
        return job.get("decideBestMemberJob")
                .start(decideBestMemberStep())
                .build();
    }
}

