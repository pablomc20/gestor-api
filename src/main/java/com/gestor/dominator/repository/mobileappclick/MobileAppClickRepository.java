package com.gestor.dominator.repository.mobileappclick;

import java.util.List;

import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRq;
import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRs;

public interface MobileAppClickRepository {

    MobileAppClickRs createClick(MobileAppClickRq rq);

    List<MobileAppClickRs> getClicksByEventType(String eventType);
}
