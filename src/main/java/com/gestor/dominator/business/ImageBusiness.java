package com.gestor.dominator.business;

import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;
import com.gestor.dominator.repository.images.ImageRepository;
import com.gestor.dominator.service.image.ImageDbService;
import org.springframework.stereotype.Service;


@Service
public class ImageBusiness implements ImageDbService {

    private final ImageRepository imageRepository;

    public ImageBusiness(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

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
