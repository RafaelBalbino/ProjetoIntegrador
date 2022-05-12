package com.generation.caiv.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.caiv.model.Usuario;

public class UserDetailsImpl implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	//autoriza os privilégios de usuário
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(Usuario usuario) {
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}
	
	//metodos padrões do basic security
	
	//garantir privilégios iguais a todos usuários
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {

		return userName;
	}
	//se a conta não está expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	//se a conta não está bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//se a credencial não está expirada
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//se a conta esta ativa
	@Override
	public boolean isEnabled() {
		return true;
	}
}