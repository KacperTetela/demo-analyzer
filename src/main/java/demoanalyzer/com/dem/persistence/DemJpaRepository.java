package demoanalyzer.com.dem.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemJpaRepository extends JpaRepository<DemEntity, Long> {}
