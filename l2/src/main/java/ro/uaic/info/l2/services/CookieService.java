package ro.uaic.info.l2.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface CookieService {
    String read(HttpServletRequest request, String cookieName);
    void createOrUpdateCookies(HttpServletResponse response, Map<String, String> cookies);
}
