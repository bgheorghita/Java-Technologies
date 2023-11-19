package ro.uaic.info.l7.services;

public interface PasswordEncoder {
    boolean verify(String rawPassword, String encodedPassword);
    String encode(String rawPassword, int cost);
}
