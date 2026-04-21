package com.gestor.dominator.service.chape;

import java.util.List;
import com.gestor.dominator.dto.chapes.ChapeRecord;
import com.gestor.dominator.dto.chapes.ChapeResult;

public interface ChapeService {
    List<ChapeResult> getAllChapes();

    ChapeResult createChape(ChapeRecord chapeRecord);

    ChapeResult updateChape(ChapeRecord chapeRecord, String chapeId);

    void deleteChape(String chapeId);
}
