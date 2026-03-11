package com.gestor.dominator.service.projects;

import org.springframework.stereotype.Service;

import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRs;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectDbServiceImpl implements ProjectDbService {

    private final ProjectImageRepository projectImageRepository;

    @Override
    public CreateProjectImageRs save(CreateProjectImageRq rq) {
        return projectImageRepository.save(rq);
    }
    
}
