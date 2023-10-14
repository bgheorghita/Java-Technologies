package ro.uaic.info.l2.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = {"/input.jsp"})
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String ipAddress = request.getRemoteAddr();
        String requestMethod = request.getMethod();
        String requestURL = request.getRequestURL().toString();
        String currentTime = LocalDateTime.now().toString();

        System.out.println("IP Address: " + ipAddress);
        System.out.println("Request Method: " + requestMethod);
        System.out.println("Requested URL: " + requestURL);
        System.out.println("Timestamp: " + currentTime);
        System.out.println();

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
