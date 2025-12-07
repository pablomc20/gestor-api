package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.CreateProjectRq;
import com.gestor.dominator.dto.projects.CreateProjectRs;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.ContractMapper;
import com.gestor.dominator.mapper.ProjectMapper;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectBusiness implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ContractRepository contractRepository;
  private final ProjectMapper projectMapper;
  private final ContractMapper contractMapper;

  @Override
  public List<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq) {
    return projectRepository.getProyectClientById(detailsForClientRq);
  }

  @Override
  @Transactional
  public CreateProjectRs createNewProject(CreateProjectRq createProject) {
    CreateProjectRecord createProjectRecord = projectMapper.toRecord(createProject);

    // Crear proyecto en la BD
    CreateProjectResult createProjectResult = projectRepository.createProject(createProjectRecord);

    if (createProjectResult.status().equals("ok")) {
      String idProject = createProjectResult.project_id();

      // FN - Crear contrato en la BD
      createContract(createProject, idProject);

      CreateProjectRs createProjectRs = projectMapper.toRs(createProjectResult);

      return createProjectRs;
    }
    throw new PostgreDbException("Error al crear un nuevo proyecto");
  }

  private void createContract(CreateProjectRq createProject, String idProject) {
    CreateContractRecord createContractRecord = contractMapper.toRecord(createProject, idProject);

    // Crear contrato en la BD
    CreateContractResult createContractResult = contractRepository.createContract(createContractRecord);

    if (!createContractResult.status().equals("ok")) {
      throw new PostgreDbException("Error al crear un nuevo contrato");
    }
  }
}
