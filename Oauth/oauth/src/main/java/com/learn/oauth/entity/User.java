package com.learn.oauth.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User extends BaseIdEntity implements UserDetails {

	private static final long serialVersionUID=1L;
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	
	@Column(name="acccount_locked")
	private boolean accountNonLocked;
	
	@Column(name="account_expired")
	private boolean accountNonExpired;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="role_user",joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "id")}
	,inverseJoinColumns = {
			@JoinColumn(name="role_id",referencedColumnName = "id")
	})
	private List<Role> roles;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
		
		roles.forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			r.getPermissions().forEach(p->{
				authorities.add(new SimpleGrantedAuthority(p.getName()));
			});
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
