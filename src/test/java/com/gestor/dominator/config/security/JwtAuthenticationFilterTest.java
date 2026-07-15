package com.gestor.dominator.config.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.dominator.service.config.CustomUserDetailsService;
import com.gestor.dominator.service.config.JwtUtil;

class JwtAuthenticationFilterTest {

    @Test
    void shouldNotRequireAuthenticationForProjectsImagesRootPath() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                Mockito.mock(JwtUtil.class),
                Mockito.mock(CustomUserDetailsService.class),
                new ObjectMapper());

        Method requiresAuthentication = JwtAuthenticationFilter.class.getDeclaredMethod("requiresAuthentication", String.class);
        requiresAuthentication.setAccessible(true);

        boolean forRootPath = (boolean) requiresAuthentication.invoke(filter, "/projects/images");
        boolean forNestedPath = (boolean) requiresAuthentication.invoke(filter, "/projects/images/abc");

        assertThat(forRootPath).isFalse();
        assertThat(forNestedPath).isFalse();
    }
}
