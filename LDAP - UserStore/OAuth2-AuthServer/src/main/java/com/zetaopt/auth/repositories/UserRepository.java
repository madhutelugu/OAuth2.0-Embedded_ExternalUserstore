package com.zetaopt.auth.repositories;



import org.springframework.data.ldap.repository.LdapRepository;

import com.zetaopt.auth.entities.User;

/**
 * User repository for CRUD operations.
 */
public interface UserRepository extends LdapRepository<User> {
    User findByUsername(String username);
    User findByDn(String username);
}
