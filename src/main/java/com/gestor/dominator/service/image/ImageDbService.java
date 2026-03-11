package com.gestor.dominator.service.image;

import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;

public interface ImageDbService {

    ImageRs findById(String id);

    ImageCreateRs save(ImageRq imageRq);

    void deleteById(String id);
}

