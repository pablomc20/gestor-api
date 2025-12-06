package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.service.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectBusiness implements ProjectService {

  // private final ProjectRepository projectRepository;

  @Override
  public Optional<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq) {
    return Optional.empty();
  }
}
