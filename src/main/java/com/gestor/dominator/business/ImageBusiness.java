package com.gestor.dominator.business;

import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;
import com.gestor.dominator.repository.ImageRepository;
import com.gestor.dominator.service.image.ImageDbService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageBusiness implements ImageDbService {

    private final ImageRepository imageRepository;

    @Override
    public ImageRs findById(String id) {
        return imageRepository.findById(id);
    }

    @Override
    public ImageCreateRs save(ImageRq imageRq) {
        return imageRepository.save(imageRq);
    }

    @Override
    public void deleteById(String id) {
        imageRepository.deleteById(id);
    }

}
