package com.dungi.core.infrastructure.jpa.notice;

import com.dungi.core.domain.notice.model.Notice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeJpaRepository extends CrudRepository<Notice, Long> {

}
