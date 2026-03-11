package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.ContractPayload;
import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.PaymentPayload;
import com.gestor.dominator.dto.projects.StatusProjectRecord;
import com.gestor.dominator.dto.projects.StatusProjectResult;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdRecord;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdResult;
import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.mapper.ContractMapper;
import com.gestor.dominator.mapper.PaymentMapper;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.contract.ContractRepository;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRs;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRepository;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.repository.payment.PaymentRepository;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.service.projects.ProjectService;
import com.gestor.dominator.constants.StatusProject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectBusiness implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;
    private final ProjectMapper projectMapper;
    private final PaymentMapper paymentMapper;
    private final ContractMapper contractMapper;

    @Override
    public List<DetailsByIdResult> getProyectEmployeeById(DetailsByIdRecord detailsForClientRecord) {
        DetailsByIdRq detailsForClientRq = projectMapper.toDetailsRq(detailsForClientRecord);

        List<DetailsByIdRs> detailsForClientRs = projectRepository.getProyectClientById(detailsForClientRq);

        return projectMapper.toDetailsRs(detailsForClientRs);
    }

    @Override
    @Transactional
    public CreateProjectResult createNewProject(CreateProjectRecord createProject) {
        CreateProjectRq createProjectRecord = projectMapper.toCreateRq(createProject);

        // Crear proyecto en la BD
        CreateProjectRs createProjectResult = projectRepository.createProject(createProjectRecord);

        String idProject = createProjectResult.project_id();

        // FN - Crear contrato en la BD
        UUID idContract = createContract(createProject.contract(), idProject);

        // FN - Crear pagos en la BD
        createPayments(createProject.payments(), idContract);

        CreateProjectResult createProjectRs = projectMapper.toResult(createProjectResult);

        return createProjectRs;
    }

    @Override
    public StatusProjectResult retrieveStatusById(StatusProjectRecord statusProjectRecord) {
        String currentStatus = projectRepository.getStatusById(UUID.fromString(statusProjectRecord.projectId()));

        if (currentStatus.isEmpty()) {
            throw new DataValidationException("No se pudo obtneer el estado del proyecto");
        }

        String nextStatus = StatusProject.retrieveNextStatusProject(currentStatus);

        return projectMapper.toStatusProjectResult(currentStatus, nextStatus);
    }

    // ****** MÉTODOS AUXILIARES ******
    private void createPayments(PaymentPayload[] payments, UUID idContract) {
        for (PaymentPayload payment : payments) {
            PaymentCreateRq createPaymentRq = paymentMapper.toPaymentCreateRq(payment, idContract);

            // Registrar pago en la BD
            PaymentCreateRs createPaymentRs = paymentRepository.createPayment(createPaymentRq);

            log.info("Pago registrado con éxito: {}", createPaymentRs.payment_id());
        }
    }

    private UUID createContract(ContractPayload createContract, String idProject) {
        CreateContractRq createContractRecord = contractMapper.toCreateContractRq(createContract, idProject);

        // Registrar contrato en la BD
        CreateContractRs createContractRs = contractRepository.createContract(createContractRecord);

        log.info("Contrato creado con éxito: {}", createContractRs.contract_id());
        return createContractRs.contract_id();
    }
}
