package com.gestor.dominator.business;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.interested.CreateRecord;
import com.gestor.dominator.dto.interested.CreateResult;
import com.gestor.dominator.repository.InteresatedRepository;
import com.gestor.dominator.service.interested.InteresatedService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestedBusiness implements InteresatedService {

    private final InteresatedRepository interesatedRepository;

    public CreateResult createInteresated(CreateRecord record) {
        return CreateResult.builder()
                .registered(interesatedRepository.createInteresated(record.email()))
                .build();
    }

}
