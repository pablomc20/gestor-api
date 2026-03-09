package com.gestor.dominator.business.project;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.projects.ContractPayload;
import com.gestor.dominator.dto.projects.PaymentPayload;
import com.gestor.dominator.dto.projects.ProjectDetailsRecord;
import com.gestor.dominator.dto.projects.ProjectDetailsResult;
import com.gestor.dominator.dto.projects.ProjectPayload;
import com.gestor.dominator.dto.projects.UserPayload;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.contract.ContractRepository;
import com.gestor.dominator.model.postgre.contract.ReadContractRq;
import com.gestor.dominator.model.postgre.contract.ReadContractRs;
import com.gestor.dominator.model.postgre.payment.PaymentReadRs;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;
import com.gestor.dominator.repository.payment.PaymentRepository;
import com.gestor.dominator.repository.project.ProjectRepository;
import com.gestor.dominator.repository.user.UserRepository;
import com.gestor.dominator.mapper.PaymentMapper;
import com.gestor.dominator.mapper.ProjectMapper;
import com.gestor.dominator.mapper.UserMapper;
import com.gestor.dominator.mapper.ContractMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetrieveProjectDetailsUseCase {

    final ProjectMapper projectMapper;
    final ContractMapper contractMapper;
    final PaymentMapper paymentMapper;
    final ProjectRepository projectRepository;
    final ContractRepository contractRepository;
    final PaymentRepository paymentRepository;
    final UserRepository userRepository;
    final UserMapper userMapper;

    public ProjectDetailsResult execute(ProjectDetailsRecord projectDetailsRecord) {
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

        String userClient = projectDetailsRs.project().user_client();
        String userEmployee = projectDetailsRs.project().user_employee();

        return ProjectDetailsResult.builder()
                .project(project)
                .contract(contract)
                .payments(orderedPayments)
                .users(getUsersByIds(userClient, userEmployee))
                .build();
    }

    public PaymentPayload[] sortPayments(PaymentPayload[] payments) {

        return Arrays.stream(payments).sorted(Comparator.comparingInt(p -> p.type().getOrder()))
                .toArray(PaymentPayload[]::new);

    }

    private List<UserPayload> getUsersByIds(String userClient, String userEmployee) {
        GetUserByIdRq clientRq = GetUserByIdRq.builder()
                .userId(UUID.fromString(userClient)).build();
        GetUserByIdRq employeeRq = GetUserByIdRq.builder()
                .userId(UUID.fromString(userEmployee)).build();

        GetUserByIdRs clientRs = userRepository.getUserDetailsById(clientRq);
        GetUserByIdRs employeeRs = userRepository.getUserDetailsById(employeeRq);

        return List.of(userMapper.toUserPayload(clientRs, userClient),
                userMapper.toUserPayload(employeeRs, userEmployee));
    }
}
