package com.dungi.message.kafka.listener.notification;

import com.dungi.core.domain.notification.model.Notification;
import com.dungi.core.domain.notification.query.NotificationDetail;
import com.dungi.core.integration.message.notification.NotificationMessageListener;
import com.dungi.core.integration.store.notification.NotificationStore;
import com.dungi.message.kafka.config.NotificationMultiValueCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Conditional(NotificationMultiValueCondition.class)
@Component
@RequiredArgsConstructor
public class NotificationMessageSubscriber {
    private final NotificationStore notificationStore;
    private final NotificationMessageListener notificationMessageListener;

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void notify(NotificationDetail notificationDetail) {
        var notification = Notification.builder()
                .senderId(notificationDetail.getSenderId())
                .receiverId(notificationDetail.getReceiverId())
                .type(notificationDetail.getType())
                .build();
        notificationStore.saveNotification(notification);
        notificationMessageListener.sendNotificationMessage(notificationDetail);
    }
}
