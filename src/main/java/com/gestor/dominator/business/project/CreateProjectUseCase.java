package com.gestor.dominator.business.project;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.service.projects.ProjectDbService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProjectUseCase {

    private final ProjectDbService projectDbService;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;
    private final ProjectMapper projectMapper;
    private final PaymentMapper paymentMapper;
    private final ContractMapper contractMapper;

    @Transactional
    public CreateProjectResult execute(CreateProjectRecord createProject) {
        CreateProjectRq createProjectRecord = projectMapper.toCreateRq(createProject);

        // Crear proyecto en la BD
        CreateProjectRs createProjectResult = projectDbService.createProject(createProjectRecord);

        String idProject = createProjectResult.project_id();

        // FN - Crear contrato en la BD
        UUID idContract = createContract(createProject.contract(), idProject);

        // FN - Crear pagos en la BD
        createPayments(createProject.payments(), idContract);

        return projectMapper.toResult(createProjectResult);
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
