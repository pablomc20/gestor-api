package com.gestor.dominator.service.product;

import com.gestor.dominator.dto.auth.AuthResponse;
import com.gestor.dominator.dto.auth.LoginRequest;
import com.gestor.dominator.dto.auth.RegisterRequest;

public interface UserService {
  AuthResponse authLogin(LoginRequest loginRequest);

  void registerNewUser(RegisterRequest registerRequest);
}
