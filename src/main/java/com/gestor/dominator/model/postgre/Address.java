package com.gestor.dominator.model.postgre;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address {

  @Id
  @Column(name = "address_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID addressId;

  private String street;
  private Integer number;
  private String neighborhood;
  private String city;
  private String zipCode;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "user_detail_id")
  private UserDetail userDetail;
}
