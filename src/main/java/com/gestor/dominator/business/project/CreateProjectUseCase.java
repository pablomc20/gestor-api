package com.gestor.dominator.business.project;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestor.dominator.constants.StatusProject;
import com.gestor.dominator.dto.projects.ContractPayload;
import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.PaymentPayload;
import com.gestor.dominator.mapper.ContractMapper;
import com.gestor.dominator.mapper.PaymentMapper;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.contract.ContractRepository;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.repository.payment.PaymentRepository;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.repository.projectstatus.ProjectStatusRepository;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRq;
import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRs;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectMapper projectMapper;
    private final PaymentMapper paymentMapper;
    private final ContractMapper contractMapper;

    public CreateProjectUseCase(ProjectRepository projectRepository, ContractRepository contractRepository,
            PaymentRepository paymentRepository, ProjectStatusRepository projectStatusRepository, ProjectMapper projectMapper, PaymentMapper paymentMapper,
            ContractMapper contractMapper) {
        this.projectRepository = projectRepository;
        this.contractRepository = contractRepository;
        this.paymentRepository = paymentRepository;
        this.projectStatusRepository = projectStatusRepository;
        this.projectMapper = projectMapper;
        this.paymentMapper = paymentMapper;
        this.contractMapper = contractMapper;
    }

    @Transactional
    public CreateProjectResult execute(CreateProjectRecord createProject) {
        Boolean hasContract = createProject.contract() != null;
        CreateProjectRq createProjectRecord = projectMapper.toCreateRq(createProject, hasContract);

        // Crear proyecto en la BD
        CreateProjectRs createProjectResult = projectRepository.createProject(createProjectRecord);

        UUID idProject = createProjectResult.project_id();

        if (createProject.payments() == null || createProject.payments().length == 0) {
            log.info("No se proporcionaron pagos para el proyecto con ID: {}", idProject);
            return projectMapper.toResult(createProjectResult);
        }

        // FN - Crear contrato en la BD
        BigDecimal totalPaymentAmount = calculateTotalPaymentAmount(createProject.payments());
        if (hasContract) {
            // Registrar contrato en la BD
            UUID idCOntractCreated = createContract(createProject.contract(), idProject, totalPaymentAmount);
            log.info("Contrato creado : {}", idCOntractCreated);
        }
        else {
            // Al no definir un contrato, se crea un estatus de finalizado para el proyecto
            UUID idClient = createProject.client();
            CreateProjectStatusRq createProjectStatusRq = new CreateProjectStatusRq(idProject, StatusProject.DELIVERED.name(), idClient);
            projectStatusRepository.createProjectStatus(createProjectStatusRq);

        }
        
        // FN - Crear pagos en la BD
        createPayments(createProject.payments(), idProject);

        return projectMapper.toResult(createProjectResult);
    }

    // ****** MÉTODOS AUXILIARES ******
    private void createPayments(PaymentPayload[] payments, UUID idProject) {
        for (PaymentPayload payment : payments) {
            PaymentCreateRq createPaymentRq = paymentMapper.toPaymentCreateRq(payment, idProject);

            // Registrar pago en la BD
            PaymentCreateRs createPaymentRs = paymentRepository.createPayment(createPaymentRq);

            log.info("Pago registrado con éxito: {}", createPaymentRs.payment_id());
        }
    }

    private UUID createContract(ContractPayload createContract, UUID idProject, BigDecimal totalPaymentAmount) {
        ContractPayload contractWithAmount = createContract.withAmount(totalPaymentAmount); 
        CreateContractRq createContractRecord = contractMapper.toCreateContractRq(contractWithAmount, idProject);
        // Registrar contrato en la BD
        CreateContractRs createContractRs = contractRepository.createContract(createContractRecord);

        return createContractRs.contract_id();

    }

    private BigDecimal calculateTotalPaymentAmount(PaymentPayload[] payments) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PaymentPayload payment : payments) {
            totalAmount = totalAmount.add(payment.amount());
        }
        return totalAmount;
    }
}
