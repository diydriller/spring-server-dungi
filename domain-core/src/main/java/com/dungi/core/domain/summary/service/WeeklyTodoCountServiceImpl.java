package com.dungi.core.domain.summary.service;

import com.dungi.common.util.TimeUtil;
import com.dungi.core.domain.summary.dto.WeeklyTodoCountDto;
import com.dungi.core.domain.user.model.User;
import com.dungi.core.infrastructure.store.room.RoomStore;
import com.dungi.core.infrastructure.store.summary.WeeklyTodoCountStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyTodoCountServiceImpl implements WeeklyTodoCountService {
    private final WeeklyTodoCountStore weeklyTodoCountStore;
    private final RoomStore roomStore;

    @Transactional(readOnly = true)
    public WeeklyTodoCountDto getWeeklyTodoCount(Long roomId) {
        var room = roomStore.getRoom(roomId);
        var memberList = roomStore.getAllMemberInRoom(room);
        var weeklyTodoCountMap = initializeWeeklyTodoCountMap(memberList);

        var weeklyTodoCountList = weeklyTodoCountStore.getWeeklyTodoCountListInRoom(roomId);
        for (var weeklyTodoCount : weeklyTodoCountList) {
            weeklyTodoCountMap.get(weeklyTodoCount.getDayOfWeek() - 1)
                    .merge(weeklyTodoCount.getUserId(), 1L, Long::sum);
        }

        return WeeklyTodoCountDto.builder()
                .memberInfoList(
                        memberList.stream()
                                .map(WeeklyTodoCountDto.MemberInfo::from)
                                .collect(Collectors.toList())
                )
                .weeklyTodoCountMap(weeklyTodoCountMap)
                .build();
    }

    private Map<Integer, Map<Long, Long>> initializeWeeklyTodoCountMap(List<User> memberList) {
        var weeklyTodoCountMap = new HashMap<Integer, Map<Long, Long>>();
        for (var day : TimeUtil.DAY.values()) {
            int dayNum = day.ordinal();
            for (var member : memberList) {
                var todoCountMap = new HashMap<Long, Long>();
                todoCountMap.put(member.getId(), 0L);
                weeklyTodoCountMap.put(dayNum, todoCountMap);
            }
        }
        return weeklyTodoCountMap;
    }
}
