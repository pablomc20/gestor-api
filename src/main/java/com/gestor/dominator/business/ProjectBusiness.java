package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.repository.ProjectRepository;
import com.gestor.dominator.service.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectBusiness implements ProjectService {

  private final ProjectRepository projectRepository;

  @Override
  public Optional<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq) {
    return projectRepository.getDetailsProjectClient(detailsForClientRq.projectId())
        .map(projection -> DetailsForClientRs.builder()
            .title(projection.getTitle())
            .startDate(projection.getStartDate())
            .estimatedCompletionDate(projection.getEstimatedCompletionDate())
            .finalAmount(projection.getFinalAmount())
            .projectId(projection.getProjectId())
            .status(projection.getStatus())
            .requestId(projection.getRequestId())
            .type(projection.getType())
            .numberPayments(projection.getNumberPayments())
            .currentPayment(projection.getCurrentPayment())
            .phoneEmployee(projection.getPhoneEmployee())
            .build());
  }
}
