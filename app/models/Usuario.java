package models;

import javax.persistence.*;

import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.Required;


@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;

	@Required
	@NonEmpty
	private String	nome;
	
	@Required
	@NonEmpty
	private String	email;
	
	@Required
	@NonEmpty
	private String	senha;
	
	public Usuario() {}

	public Usuario(String nome, String email, String senha) {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}
	
	public boolean validateLogin(String email, String senha){
		if(email.equals(this.email) & senha.equals(this.senha)){
			return true;
		}
		return false;
	}
	
	
}
