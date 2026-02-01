package com.gestor.dominator.business;

import com.gestor.dominator.dto.projects.ContractPayload;
import com.gestor.dominator.dto.projects.CreateProjectRecord;
import com.gestor.dominator.dto.projects.CreateProjectResult;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.dto.projects.PaymentPayload;
import com.gestor.dominator.dto.projects.ProjectPayload;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.ContractMapper;
import com.gestor.dominator.mapper.PaymentMapper;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.model.postgre.contract.ContractRepository;
import com.gestor.dominator.model.postgre.contract.CreateContractRq;
import com.gestor.dominator.model.postgre.contract.CreateContractRs;
import com.gestor.dominator.model.postgre.contract.ReadContractRq;
import com.gestor.dominator.model.postgre.contract.ReadContractRs;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRq;
import com.gestor.dominator.model.postgre.payment.PaymentCreateRs;
import com.gestor.dominator.model.postgre.payment.PaymentReadRs;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.repository.payment.PaymentRepository;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.service.projects.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
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
    public List<DetailsForEmployeeResult> getProyectClientById(DetailsForEmployeeRecord detailsForClientRecord) {
        DetailsForEmployeeRq detailsForClientRq = projectMapper.toDetailsRq(detailsForClientRecord);

        List<DetailsForClientRs> detailsForClientRs = projectRepository.getProyectClientById(detailsForClientRq);

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
    public ProjectDetailsResult getProjectDetailsById(ProjectDetailsRecord projectDetailsRecord) {
        ProjectDetailsRq projectDetailsRq = projectMapper.toDetailsProjectRq(projectDetailsRecord);

        // FN - Obtener detalles del proyecto
        ProjectDetailsRs projectDetailsRs = projectRepository.getProjectDetailsById(projectDetailsRq);
        if (projectDetailsRs == null) {
            throw new PostgreDbException("No se encontro el proyecto");
        }
        ProjectPayload project = projectMapper.toDetailsProjectRs(projectDetailsRs,
                projectDetailsRecord.projectId().toString());

        // FN - Obtener detalles del contrato
        ReadContractRs readContractRs = contractRepository.getContractDetailsById(
                ReadContractRq.builder().project_id(projectDetailsRecord.projectId()).build());
        // FN - Obtener detalles de los pagos
        List<PaymentReadRs> paymentsRs = paymentRepository.getPayments(UUID.fromString(readContractRs.contract_id()));
        Long numberPaymentPaid = paymentsRs.stream().filter(p -> p.paid()).count();

        ContractPayload contract = contractMapper.toDetailsContractRs(readContractRs, numberPaymentPaid.intValue());

        PaymentPayload[] payments = paymentMapper.toPayloads(paymentsRs);
        // Ordenar pagos por su propiedad type en este orden (FIRST, SECOND y FINAL)
        PaymentPayload[] orderedPayments = sortPayments(payments);

        return ProjectDetailsResult.builder()
                .project(project)
                .contract(contract)
                .payments(orderedPayments)
                .build();
    }

    // ****** MÉTODOS AUXILIARES ******
    public PaymentPayload[] sortPayments(PaymentPayload[] payments) {

        return Arrays.stream(payments)
                .sorted(Comparator.comparingInt(p -> switch (p.type()) {
                    case "FIRST" -> 1;
                    case "SECOND" -> 2;
                    case "FINAL" -> 3;
                    default -> Integer.MAX_VALUE;
                }))
                .toArray(PaymentPayload[]::new);
    }

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
