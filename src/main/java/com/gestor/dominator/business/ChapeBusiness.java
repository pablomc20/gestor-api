package com.gestor.dominator.business;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.chapes.ChapeRecord;
import com.gestor.dominator.dto.chapes.ChapeResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.ChapeMapper;
import com.gestor.dominator.model.postgre.chape.ChapeRq;
import com.gestor.dominator.repository.chape.ChapeRepository;
import com.gestor.dominator.service.chape.ChapeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapeBusiness implements ChapeService {

    private final ChapeRepository chapeRepository;
    private final ChapeMapper chapeMapper;

    @Cacheable("chapes")
    @Override
    public List<ChapeResult> getAllChapes() {
        return chapeMapper.toGetAllChapeResult(chapeRepository.getAllChapes());
    }

    @CacheEvict(value = "chapes", allEntries = true)
    @Override
    public ChapeResult createChape(ChapeRecord chapeRecord) {
        ChapeRq chapeRq = chapeMapper.toRecordRq(chapeRecord);
        return chapeMapper.toChapeResult(chapeRepository.createChape(chapeRq));
    }

    @CacheEvict(value = "chapes", allEntries = true)
    @Override
    public ChapeResult updateChape(ChapeRecord chapeRecord, String chapeId) {
        ChapeRq chapeRq = chapeMapper.toRecordRq(chapeRecord);
        return chapeMapper.toChapeResult(chapeRepository.updateChape(chapeRq, chapeId));
    }

    @CacheEvict(value = "chapes", allEntries = true)
    @Override
    public void deleteChape(String chapeId) {
        Integer result = chapeRepository.deleteChape(chapeId);
        if (result == 0) {
            throw new PostgreDbException("Chape not found");
        }
    }

}
