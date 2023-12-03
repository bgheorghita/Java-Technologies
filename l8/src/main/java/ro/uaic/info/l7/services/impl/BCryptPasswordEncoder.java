package ro.uaic.info.l7.services.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ro.uaic.info.l7.services.PasswordEncoder;

import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless
public class BCryptPasswordEncoder implements PasswordEncoder, Serializable {
    // 2^10 = 1,024 iterations
    public final static int COST_10 = 10;
    // 2^12 = 4,096 iterations
    public final static int COST_12 = 12;
    // 2^14 = 16,384 iterations
    public final static int COST_14 = 14;

    @Override
    public boolean verify(String rawPassword, String encodedPassword) {
        return getVerifyer().verify(rawPassword.getBytes(), encodedPassword.getBytes()).verified;
    }

    @Override
    public String encode(String rawPassword, int cost) {
        return getHasher().hashToString(cost, rawPassword.toCharArray());
    }

    private BCrypt.Hasher getHasher() {
        return BCrypt.withDefaults();
    }

    private BCrypt.Verifyer getVerifyer() {
        return BCrypt.verifyer();
    }
}
