package school.sptech.zup.configuration.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.management.BadAttributeValueExpException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        if (authException.getClass().equals(BadCredentialsException.class)){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
