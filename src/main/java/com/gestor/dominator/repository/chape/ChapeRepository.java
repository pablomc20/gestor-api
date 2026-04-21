package com.gestor.dominator.repository.chape;

import java.util.List;
import com.gestor.dominator.model.postgre.chape.ChapeRq;
import com.gestor.dominator.model.postgre.chape.ChapeRs;

public interface ChapeRepository {
    List<ChapeRs> getAllChapes();

    ChapeRs createChape(ChapeRq chapeRq);

    ChapeRs updateChape(ChapeRq chapeRq, String chapeId);

    Integer deleteChape(String chapeId);
}
