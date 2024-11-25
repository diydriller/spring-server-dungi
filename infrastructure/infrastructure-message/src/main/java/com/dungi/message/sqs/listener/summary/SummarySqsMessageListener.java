package com.dungi.message.sqs.listener.summary;

import com.dungi.core.domain.summary.event.SaveNoticeVoteEvent;
import com.dungi.core.domain.summary.event.UpdateWeeklyTodoCountEvent;
import com.dungi.core.domain.summary.model.NoticeVote;
import com.dungi.core.domain.summary.model.WeeklyTodoCount;
import com.dungi.core.infrastructure.store.summary.NoticeVoteStore;
import com.dungi.core.infrastructure.store.summary.WeeklyStatisticStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

@ConditionalOnProperty(name = "message.kind", havingValue = "sqs")
@RequiredArgsConstructor
@Component
public class SummarySqsMessageListener {
    private final ObjectMapper objectMapper;
    private final NoticeVoteStore noticeVoteStore;
    private final WeeklyStatisticStore weeklyStatisticStore;

    @SqsListener("diydriller-sqs")
    public void listen(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String type = jsonNode.get("type").asText();

            switch (type) {
                case "save-notice-vote":
                    var saveNoticeVoteEvent = objectMapper.readValue(jsonNode.get("data").toString(), SaveNoticeVoteEvent.class);
                    handleSaveNoticeVoteEvent(saveNoticeVoteEvent);
                    break;
                case "update-weekly-todo-count":
                    var updateWeeklyTodoCountEvent = objectMapper.readValue(jsonNode.get("data").toString(), UpdateWeeklyTodoCountEvent.class);
                    handleUpdateWeeklyTodoCount(updateWeeklyTodoCountEvent);
                    break;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleSaveNoticeVoteEvent(SaveNoticeVoteEvent event) {
        noticeVoteStore.saveNoticeVote(
                NoticeVote.builder()
                        .content(event.getContent())
                        .noticeVoteId(event.getId())
                        .createdTime(event.getCreatedTime())
                        .roomId(event.getRoomId())
                        .userId(event.getUserId())
                        .type(event.getType())
                        .build()
        );
    }

    private void handleUpdateWeeklyTodoCount(UpdateWeeklyTodoCountEvent event) {
        var now = LocalDate.now();
        var weekFields = WeekFields.ISO;
        var weekOfYear = now.get(weekFields.weekOfYear());
        var dayOfWeek = now.get(weekFields.dayOfWeek());
        var year = now.getYear();

        weeklyStatisticStore.getWeeklyTodoCountByUniqueKeys(
                event.getRoomId(),
                event.getUserId(),
                year,
                weekOfYear,
                dayOfWeek
        ).ifPresentOrElse(
                WeeklyTodoCount::addTodoCount,
                () -> {
                    var weeklyTodoCount = WeeklyTodoCount.builder()
                            .roomId(event.getRoomId())
                            .userId(event.getUserId())
                            .year(year)
                            .todoCount(1L)
                            .weekOfYear(weekOfYear)
                            .dayOfWeek(dayOfWeek)
                            .build();
                    weeklyStatisticStore.saveWeeklyTodoCount(weeklyTodoCount);
                }
        );
    }
}
