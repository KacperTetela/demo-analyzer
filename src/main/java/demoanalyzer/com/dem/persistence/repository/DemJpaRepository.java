package demoanalyzer.com.dem.persistence.repository;

import demoanalyzer.com.dem.persistence.entity.DemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemJpaRepository extends JpaRepository<DemEntity, Long> {}
