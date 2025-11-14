package infrastructure.persistence.jpaRepository;

import infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}
