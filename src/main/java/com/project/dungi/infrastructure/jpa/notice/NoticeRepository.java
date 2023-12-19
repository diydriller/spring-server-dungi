package com.project.dungi.infrastructure.jpa.notice;

import com.project.dungi.domain.notice.model.Notice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

}
