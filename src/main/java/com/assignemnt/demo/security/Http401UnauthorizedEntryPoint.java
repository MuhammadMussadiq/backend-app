package com.assignemnt.demo.security;

import com.assignemnt.demo.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);

    /**
     * Always returns a 401 error code to the client.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {

        log.debug("Pre-authenticated entry point called. Rejecting access");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse responseDTO = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                Arrays.asList(exception.getMessage()));
        response.setContentType("application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(responseDTO));
    }
}
