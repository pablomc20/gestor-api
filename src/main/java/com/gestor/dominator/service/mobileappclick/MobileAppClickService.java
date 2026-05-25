package com.gestor.dominator.service.mobileappclick;

import java.util.List;

import com.gestor.dominator.dto.mobileappclick.MobileAppClickRecord;
import com.gestor.dominator.dto.mobileappclick.MobileAppClickResult;

public interface MobileAppClickService {

    MobileAppClickResult createClick(MobileAppClickRecord record);

    List<MobileAppClickResult> getClicksByEventType(String eventType);
}
