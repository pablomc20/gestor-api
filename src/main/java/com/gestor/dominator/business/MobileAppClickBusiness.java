package com.gestor.dominator.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.mobileappclick.MobileAppClickRecord;
import com.gestor.dominator.dto.mobileappclick.MobileAppClickResult;
import com.gestor.dominator.mapper.MobileAppClickMapper;
import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRq;
import com.gestor.dominator.repository.mobileappclick.MobileAppClickRepository;
import com.gestor.dominator.service.mobileappclick.MobileAppClickService;


@Service
public class MobileAppClickBusiness implements MobileAppClickService {

    private final MobileAppClickRepository mobileAppClickRepository;
    private final MobileAppClickMapper mobileAppClickMapper;

    public MobileAppClickBusiness(MobileAppClickRepository mobileAppClickRepository,
            MobileAppClickMapper mobileAppClickMapper) {
        this.mobileAppClickRepository = mobileAppClickRepository;
        this.mobileAppClickMapper = mobileAppClickMapper;
    }

    @Override
    public MobileAppClickResult createClick(MobileAppClickRecord record) {
        MobileAppClickRq rq = mobileAppClickMapper.toRq(record);
        return mobileAppClickMapper.toResult(mobileAppClickRepository.createClick(rq));
    }

    @Override
    public List<MobileAppClickResult> getClicksByEventType(String eventType) {
        return mobileAppClickMapper.toResultList(mobileAppClickRepository.getClicksByEventType(eventType));
    }
}
