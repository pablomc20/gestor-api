package com.gestor.dominator.model.postgre;

import lombok.*;

import java.util.UUID;

@Builder
public class Project {

  private UUID id;

  private String title;

  // Agrega más campos según tu tabla real si es necesario
}
