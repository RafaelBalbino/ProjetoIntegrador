package com.generation.caiv.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.caiv.model.Usuario;
import com.generation.caiv.model.UsuarioLogin;
import com.generation.caiv.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	// função para cadastrar usuário
	public Optional<Usuario> cadastraUsuario(Usuario usuario) {
		// valida se o usuário ja existe no banco
		if (repository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
		// criptografa a senha caso não exista o usuário
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		// salva a senha criptografada, dentro do banco de dados
		return Optional.of(repository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		repository.findById(usuario.getId()).isPresent();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(repository.save(usuario));
	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	public Optional<UsuarioLogin> autenticaUsuario(Optional<UsuarioLogin> usuarioLogin) {
		Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());
		if (usuario.isPresent()) {
			/*
			 * Basicamente tudo o que achar no banco de dados é construído no objeto
			 * 'usuarioLogin' (Alimentação) Alimentação é a inserção dos atributos de um
			 * usuário de dentro do banco de dados para "montar" um objeto chamado
			 * "usuarioLogin"
			 */
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get()
						.setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				return usuarioLogin;
			}
		}
		return Optional.empty();
	}

	// função para encriptar a senha
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}

	/*
	 * função que gera um token para cada caracter do usuario e senha Transforma
	 * usuario e senha em byte e depois retorna todos os bytes em forma de String
	 */
	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

}