package com.zetaopt.auth.util;

import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;

public class GenericUtil {
	
	public static LdapQueryBuilder getLdapQuery(String usernameWithTenant) {
		  	
			String[] tokens = usernameWithTenant.split("@");
		 	
		 	String tempUsername = tokens[0];
		 	String tenant = tokens[1];
		    
		    LdapQueryBuilder ldapQuery = LdapQueryBuilder.query();
		    ldapQuery.base("ou="+tenant);
		    ldapQuery.searchScope(SearchScope.SUBTREE);
		    ldapQuery.filter("(cn="+tempUsername+")");
		    
		    return ldapQuery;
	}

}
