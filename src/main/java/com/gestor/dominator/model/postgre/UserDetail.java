package com.gestor.dominator.model.postgre;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetail {

  @Id
  @Column(name = "user_detail_id")
  private UUID userDetailId;

  private String name;
  private String phone;
  private String url_image;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  // getters y setters
}
