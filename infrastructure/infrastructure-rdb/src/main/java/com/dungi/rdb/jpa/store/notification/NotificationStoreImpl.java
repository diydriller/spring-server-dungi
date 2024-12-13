package com.dungi.rdb.jpa.store.notification;

import com.dungi.core.domain.notification.model.Notification;
import com.dungi.core.integration.store.notification.NotificationStore;
import com.dungi.rdb.jpa.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationStoreImpl implements NotificationStore {
    private final NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
