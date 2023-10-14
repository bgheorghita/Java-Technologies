package ro.uaic.info.l2.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class CookieServiceImpl implements CookieService {
    public static final int MAX_AGE = 7 * 24 * 60 * 60; // 7 days
    @Override
    public String read(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void createOrUpdateCookies(HttpServletResponse response, Map<String, String> cookies) {
        cookies.forEach((key, value) -> {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(MAX_AGE);
            response.addCookie(cookie);
        });
    }
}
