package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.exceptions.PessoaInvalidaException;

import org.hibernate.validator.constraints.Email;

import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.MaxLength;

@Entity
public class Participante {

//	private final String EMAIL_PATTERN =  "^[a-z][a-zA-Z0-9]*$";
	private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private final int MAXLENGTH = 70;

	@Id
	@GeneratedValue
	private long id;

	@MaxLength(value = MAXLENGTH)
	private String nome;

	@Email
	@MaxLength(value = MAXLENGTH)
	private String email;
	
	@NonEmpty
	private String senha;

	@ManyToOne
	private Evento evento;

	public Participante() {
	}

	public Participante(String nome, String email, String senha,Evento evento) throws PessoaInvalidaException{
		setSenhaConstrutor(senha);
		setNomeConstrutor(nome);
		setEmailConstrutor(email);
		setEventoConstrutor(evento);
	}

	public String getNome() {
		return nome;
	}

	private void setEventoConstrutor(Evento evento) throws PessoaInvalidaException {
		if (evento == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		this.evento = evento;
	}
	
	private void setEmailConstrutor(String email) throws PessoaInvalidaException {
		if (email == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (!email.matches(EMAIL_PATTERN)){
			throw new PessoaInvalidaException("Email inválido");
		}
		if (email.length() > MAXLENGTH){
			throw new PessoaInvalidaException("Email longo");
		}
		this.email = email;
	}
	private void setNomeConstrutor(String nome) throws PessoaInvalidaException {
		if (nome == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (nome.length() > MAXLENGTH){
			throw new PessoaInvalidaException("Nome longo");
		}
		this.nome = nome;
	}
	
	
	
	public void setNome(String nome) throws PessoaInvalidaException {
		if (nome == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (nome.length() > MAXLENGTH){
			throw new PessoaInvalidaException("Nome longo");
		}
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws PessoaInvalidaException {
		if (email == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (!email.matches(EMAIL_PATTERN)){
			throw new PessoaInvalidaException("Email inválido");
		}
		if (email.length() > MAXLENGTH){
			throw new PessoaInvalidaException("Email longo");
		}
		this.email = email;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) throws PessoaInvalidaException {
		if (evento == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		this.evento = evento;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	private void setSenhaConstrutor(String senha) {
		this.senha = senha;
	}
	
	public boolean validateLogin(String email, String senha){
		if(email.equals(this.email) & senha.equals(this.senha)){
			return true;
		}
		return false;
	}
}
