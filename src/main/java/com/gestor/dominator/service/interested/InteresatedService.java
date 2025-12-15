package com.gestor.dominator.service.interested;

import com.gestor.dominator.dto.interested.CreateRecord;
import com.gestor.dominator.dto.interested.CreateResult;

public interface InteresatedService {

    CreateResult createInteresated(CreateRecord record);

}
