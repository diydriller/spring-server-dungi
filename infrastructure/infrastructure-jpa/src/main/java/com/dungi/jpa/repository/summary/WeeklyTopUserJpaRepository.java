package com.dungi.jpa.repository.summary;

import com.dungi.core.domain.summary.model.WeeklyTopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyTopUserJpaRepository extends JpaRepository<WeeklyTopUser, Long> {
}
