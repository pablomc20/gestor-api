package com.gestor.dominator.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface DetailsForClientProjection {
  String getTitle();

  LocalDate getStartDate();

  LocalDate getEstimatedCompletionDate();

  BigDecimal getFinalAmount();

  UUID getProjectId();

  String getStatus();

  UUID getRequestId();

  String getType();

  Integer getNumberPayments();

  Integer getCurrentPayment();

  String getPhoneEmployee();
}
