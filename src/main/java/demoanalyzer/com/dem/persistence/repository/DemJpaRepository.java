package demoanalyzer.com.dem.persistence.repository;

import demoanalyzer.com.dem.persistence.entity.DemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemJpaRepository extends JpaRepository<DemEntity, Long> {

  @Query(
      "SELECT d FROM DemEntity d WHERE d.metadata.owner.id = :ownerId ORDER BY d.metadata.createdAt DESC")
  List<DemEntity> findAllForOwner(@Param("ownerId") Long ownerId);
}
