package com.tpintegrador.adapter.infrastructure.persistence.jpaRepository;

import com.tpintegrador.adapter.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}
