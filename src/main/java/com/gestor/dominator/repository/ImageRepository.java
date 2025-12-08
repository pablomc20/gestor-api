package com.gestor.dominator.repository;

import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;

public interface ImageRepository {
    ImageRs findById(String id);

    ImageCreateRs save(ImageRq imageRs);

    void deleteById(String id);
}
