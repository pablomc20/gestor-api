package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.contract.CreateContractRecord;
import com.gestor.dominator.model.postgre.contract.CreateContractResult;
import com.gestor.dominator.model.postgre.project.CreateProjectRecord;
import com.gestor.dominator.model.postgre.project.CreateProjectResult;
import com.gestor.dominator.repository.ContractRepository;
import com.gestor.dominator.repository.ProjectRepository;
import com.gestor.dominator.service.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectBusiness implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ContractRepository contractRepository;

  // @Override
  // public Optional<DetailsForClientRs> getProyectClientById(DetailsForClientRq
  // detailsForClientRq) {
  // return Optional.empty();
  // }

  @Override
  @Transactional
  public CreateProjectRs createNewProject(CreateProjectRq createProject) {
    CreateProjectRecord createProjectRecord = CreateProjectRecord.builder()
        .title(createProject.title())
        .description(createProject.description())
        .size(createProject.size())
        .startDate(createProject.startDate())
        .estimatedCompletionDate(createProject.estimatedCompletionDate())
        .budget(createProject.budget())
        .category(createProject.category())
        .client(createProject.client())
        .employee(createProject.employee())
        .colors(createProject.colors())
        .materials(createProject.materials())
        .images(createProject.images())
        .build();
    CreateProjectResult createProjectResult = projectRepository.createProject(createProjectRecord);

    if (createProjectResult.status().equals("ok")) {
      String idProject = createProjectResult.project_id();

      createContract(createProject, idProject);

      return CreateProjectRs.builder().idProject(idProject).build();
    }
    throw new PostgreDbException("Error al crear un nuevo proyecto");
  }

  private void createContract(CreateProjectRq createProject, String idProject) {
    CreateContractRecord createContractRecord = CreateContractRecord.builder()
        .final_amount(createProject.budget())
        .project_id(UUID.fromString(idProject))
        .number_payment(createProject.number_payment())
        .build();

    CreateContractResult createContractResult = contractRepository.createContract(createContractRecord);

    if (!createContractResult.status().equals("ok")) {
      throw new PostgreDbException("Error al crear un nuevo contrato");
    }
  }
}
