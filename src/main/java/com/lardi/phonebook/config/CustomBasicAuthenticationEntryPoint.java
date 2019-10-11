package com.lardi.phonebook.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lardi.phonebook.message.Message;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("code", Integer.toString(HttpServletResponse.SC_FORBIDDEN));
        map.put("message", authException.getMessage());
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print(mapper.writeValueAsString(map));
        response.getWriter().flush();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(Message.REALM.getDescription());
        super.afterPropertiesSet();
    }
}
