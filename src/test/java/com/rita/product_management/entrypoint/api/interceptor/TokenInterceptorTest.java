package com.rita.product_management.entrypoint.api.interceptor;

import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.infrastructure.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TokenInterceptorTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TokenInterceptor tokenInterceptor;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Test
    void givenNoAuthorizationHeader_whenDoFilter_thenContinueFilterChain() throws ServletException, IOException {
        request.setMethod("GET");
        request.setRequestURI("/test");
        tokenInterceptor.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void givenAuthorizationHeaderWithoutBearer_whenDoFilter_thenContinueFilterChain() throws ServletException, IOException {
        request.addHeader("Authorization", "Basic somevalue");
        tokenInterceptor.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void givenValidToken_whenDoFilter_thenSetAuthentication() throws ServletException, IOException {
        User user = User.builder().username("testuser").role(UserType.ADMIN).build();
        String token = "validToken";
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(user.getUsername());
        when(jwtUtil.extractRole(token)).thenReturn(user.getRole().name());
        when(jwtUtil.isTokenExpired(token)).thenReturn(false);
        tokenInterceptor.doFilterInternal(request, response, filterChain);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo(user.getUsername());
        assertThat(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_ADMIN");
        verify(filterChain).doFilter(request, response);
    }


    @Test
    void givenExpiredToken_whenDoFilter_thenDoNotSetAuthentication() throws ServletException, IOException {
        String token = "expiredToken";
        String username = "testuser";
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.isTokenExpired(token)).thenReturn(true);
        tokenInterceptor.doFilterInternal(request, response, filterChain);
        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil).isTokenExpired(token);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void givenInvalidToken_whenDoFilter_thenHandleExceptionAndContinueFilterChain() throws ServletException, IOException {
        String token = "invalidToken";
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));
        tokenInterceptor.doFilterInternal(request, response, filterChain);
        verify(jwtUtil).extractUsername(token);
        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

}
