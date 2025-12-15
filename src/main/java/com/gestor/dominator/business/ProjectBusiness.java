package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.ContractMapper;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.repository.ProjectRepository;
import com.gestor.dominator.repository.contract.ContractRepository;
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
  public List<DetailsForEmployeeResult> getProyectClientById(DetailsForEmployeeRecord detailsForClientRecord) {
    DetailsForEmployeeRq detailsForClientRq = projectMapper.toDetailsRq(detailsForClientRecord);

    List<DetailsForClientRs> detailsForClientRs = projectRepository.getProyectClientById(detailsForClientRq);

    return projectMapper.toDetailsRs(detailsForClientRs);
  }

  @Override
  @Transactional
  public CreateProjectResult createNewProject(CreateProjectRecord createProject) {
    CreateProjectRq createProjectRecord = projectMapper.toRq(createProject);

    // Crear proyecto en la BD
    CreateProjectRs createProjectResult = projectRepository.createProject(createProjectRecord);

    String idProject = createProjectResult.project_id();

    // FN - Crear contrato en la BD
    createContract(createProject, idProject);

    CreateProjectResult createProjectRs = projectMapper.toResult(createProjectResult);

    return createProjectRs;
  }

  private void createContract(CreateProjectRecord createProject, String idProject) {
    CreateContractRq createContractRecord = contractMapper.toRecord(createProject, idProject);

    // Crear contrato en la BD
    contractRepository.createContract(createContractRecord);
  }

  @Override
  public ProjectDetailsResult getProjectDetailsById(ProjectDetailsRecord projectDetailsRecord) {
    ProjectDetailsRq projectDetailsRq = projectMapper.toDetailsProjectRq(projectDetailsRecord);

    ProjectDetailsRs projectDetailsRs = projectRepository.getProjectDetailsById(projectDetailsRq);

    if (projectDetailsRs == null) {
      throw new PostgreDbException("Error al obtener los detalles del proyecto");
    }

    return projectMapper.toDetailsProjectRs(projectDetailsRs);
  }
}
