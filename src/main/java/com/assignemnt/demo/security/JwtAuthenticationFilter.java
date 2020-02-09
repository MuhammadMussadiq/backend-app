package com.assignemnt.demo.security;

import com.assignemnt.demo.dto.ErrorResponse;
import com.assignemnt.demo.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private final String METHOD_NOT_SUPPORTED = "Method not supported: ";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/authenticate");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    METHOD_NOT_SUPPORTED + request.getMethod());
        }

        LoginDto loginDTO;
        try {
            loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    new ArrayList<>()));
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword(),
                new ArrayList<>());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        UserDetails principal = (UserDetails) authResult.getPrincipal();

        List<String> roles =  principal.getAuthorities().stream().map(o -> o.getAuthority()).collect(Collectors.toList());

        String accessToken = JwtUtils.generateAccessToken(principal.getUsername(), roles);

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("tokenType", "Bearer");

        response.setContentType("application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(tokens));

    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        int status;
        String reasonPhrase;

        if (!StringUtils.isEmpty(failed.getMessage()) && failed.getMessage().contains(METHOD_NOT_SUPPORTED)) {
            status = HttpStatus.METHOD_NOT_ALLOWED.value();
            reasonPhrase = HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase();
        } else {
            status = HttpStatus.UNAUTHORIZED.value();
            reasonPhrase = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }
        response.setStatus(status);

        ErrorResponse responseDTO = new ErrorResponse(status, Arrays.asList(reasonPhrase, failed.getMessage()));
        response.setContentType("application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(responseDTO));
    }
}
