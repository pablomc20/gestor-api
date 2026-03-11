package com.gestor.dominator.service.projects;

import java.util.List;

import com.gestor.dominator.dto.projects.StatusProjectRecord;
import com.gestor.dominator.dto.projects.StatusProjectResult;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdRecord;
import com.gestor.dominator.dto.projects.usecase.DetailsByIdResult;

public interface ProjectService {

    List<DetailsByIdResult> getProyectEmployeeById(DetailsByIdRecord detailsForEmployeeRq);

    StatusProjectResult retrieveStatusById(StatusProjectRecord statusProjectRecord);

}
