package infrastructure.persistence.jpaRepository;

import infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJpaRepository extends JpaRepository<ProjectEntity,Long> {
    boolean existsByName(String name);
}
