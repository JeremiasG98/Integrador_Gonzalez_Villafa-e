package com.tpintegrador.adapter.infrastructure.persistence.jpaRepository;

import com.tpintegrador.adapter.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJpaRepository extends JpaRepository<ProjectEntity,Long> {
    boolean existsByName(String name);
}
