package com.gestor.dominator.repository;

import com.gestor.dominator.repository.projection.DetailsForClientProjection;
import com.gestor.dominator.model.postgre.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

  @Query(value = "SELECT * FROM get_details_project_client(:projectId)", nativeQuery = true)
  Optional<DetailsForClientProjection> getDetailsProjectClient(@Param("projectId") UUID projectId);
}
