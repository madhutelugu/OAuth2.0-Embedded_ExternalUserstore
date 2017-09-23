package com.zetaopt.auth.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.zetaopt.auth.entities.User;
import com.zetaopt.auth.repositories.UserRepository;
import com.zetaopt.auth.util.GenericUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

@Autowired
private UserRepository userRepository;


@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    System.out.println("Name: " +name); //does nothing
    
    User user = userRepository.findOne(GenericUtil.getLdapQuery(name));
    
    String password = user.getPassword();// is like a password :)

    String credentials = authentication.getCredentials().toString();
    if (password.equals(credentials)) {
        return new UsernamePasswordAuthenticationToken(authentication.getName(), new ArrayList<>(), translate(user.getRoles()));
    } else {
        return null;
    }
}


private Collection<? extends GrantedAuthority> translate(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
        String name = role.toUpperCase();
        //Make sure that all roles start with "ROLE_"
        if (!name.startsWith("ROLE_"))
            name = "ROLE_" + name;
        authorities.add(new SimpleGrantedAuthority(name));
    }
    return authorities;
}


@Override
public boolean supports(Class<?> authentication) {
    // return
    // UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    //return authentication.equals(UsernamePasswordAuthenticationToken.class);
    return true;
}

}