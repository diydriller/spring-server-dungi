package com.dungi.batchserver.batch.job;

import com.dungi.batchserver.batch.writer.JpaItemListWriter;
import com.dungi.core.domain.common.value.DeleteStatus;
import com.dungi.core.domain.room.model.Room;
import com.dungi.core.domain.summary.model.WeeklyTopUser;
import com.dungi.core.integration.store.summary.WeeklyStatisticStore;
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
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory job;
    private final EntityManagerFactory entityManagerFactory;
    private final WeeklyStatisticStore weeklyStatisticStore;
    private final StepBuilderFactory steps;
    private final int chunkSize = 10;

    @Bean
    public Step decideBestMemberStep() {
        return steps.get("decideBestMemberStep")
                .<Room, List<WeeklyTopUser>>chunk(chunkSize)
                .reader(decideBestMemberPagingReader())
                .processor(decideBestMemberProcessor())
                .writer(decideBestMemberListWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Room> decideBestMemberPagingReader() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("deleteStatus", DeleteStatus.NOT_DELETED);
        return new JpaPagingItemReaderBuilder<Room>()
                .queryString("SELECT r FROM Room r WHERE r.deleteStatus = :deleteStatus")
                .pageSize(chunkSize)
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(parameterValues)
                .name("decideBestMemberPagingReader")
                .build();
    }

    @Bean
    public ItemProcessor<Room, List<WeeklyTopUser>> decideBestMemberProcessor() {
        return room -> {
            var lastWeekDate = LocalDate.now().minusWeeks(1);
            var weekFields = WeekFields.ISO;
            var year = lastWeekDate.getYear();
            var weekOfYear = lastWeekDate.get(weekFields.weekOfYear());

            return weeklyStatisticStore.decideAndGetWeeklyTopUserInRoom(room.getId(), year, weekOfYear).stream()
                    .map(weeklyTodoCount -> {
                        var user = WeeklyTopUser.builder()
                                .userId(weeklyTodoCount.getUserId())
                                .roomId(weeklyTodoCount.getRoomId())
                                .weekOfYear(weeklyTodoCount.getWeekOfYear())
                                .year(weeklyTodoCount.getYear())
                                .build();
                        System.out.println(user);
                        return user;
                    })
                    .collect(Collectors.toList());
        };
    }

    @Bean
    public JpaItemListWriter<WeeklyTopUser> decideBestMemberListWriter() {
        JpaItemWriter<WeeklyTopUser> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return new JpaItemListWriter<>(writer, entityManagerFactory);
    }

    @Bean(name = "decideBestMateJob")
    public Job decideBestMemberJob() {
        return job.get("decideBestMemberJob")
                .start(decideBestMemberStep())
                .build();
    }
}

