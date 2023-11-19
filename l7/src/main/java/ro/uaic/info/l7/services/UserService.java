package ro.uaic.info.l7.services;

import ro.uaic.info.l7.entities.Role;
import ro.uaic.info.l7.entities.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    boolean hasRoleName(String username, String roleName);
    boolean hasAnyRole(String username);
}
