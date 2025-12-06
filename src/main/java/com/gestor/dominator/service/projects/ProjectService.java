package com.gestor.dominator.service.projects;

import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;

import java.util.Optional;

public interface ProjectService {

  Optional<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq);
}
