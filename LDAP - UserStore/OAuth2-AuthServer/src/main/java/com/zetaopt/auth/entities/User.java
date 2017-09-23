package com.zetaopt.auth.entities;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;

import org.apache.commons.codec.binary.Base64;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

@Entry(objectClasses = { "inetOrgPerson", "top" })
public class User {

	@Id
	private Name dn;
	@Attribute(name = "cn")
    private String username;
	@Attribute(name = "pager")
    private String password;
    @Attribute(name = "employeeType")
    private List<String> roles;
    @Transient
    private String rdn;
    @Attribute(name = "businessCategory")
    private String jsonPayload;
    
    public User() {}

    public User(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    public String getJsonPayload() {
		return jsonPayload;
	}

	public void setJsonPayload(String jsonPayload) {
		this.jsonPayload = jsonPayload;
	}

	public String getRdn() {
		return this.dn.toString();
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
    	Base64 base64 = new Base64();
    	return new String(base64.decode(this.password.getBytes()));
    }

    public void setPassword(String password) {
    	Base64 base64 = new Base64();
    	this.password = new String(base64.encode(password.getBytes()));
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
