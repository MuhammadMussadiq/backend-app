package com.assignemnt.demo.security;

import com.assignemnt.demo.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Access Denied handler [403]
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        ErrorResponse responseDTO = new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                Arrays.asList(HttpStatus.FORBIDDEN.getReasonPhrase(), accessDeniedException.getMessage()));
        response.setContentType("application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(responseDTO));
    }

}
