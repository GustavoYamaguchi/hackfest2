package controllers;


import static play.data.Form.form;

import java.util.List;

import models.Participante;
import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.db.jpa.Transactional;
import views.html.*;

public class Login extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	static Form<Usuario> loginForm = form(Usuario.class).bindFromRequest();

	@Transactional
    public static Result show() {
		if (session().get("user") != null) {
			return redirect(routes.Application.index());
		}
        return ok(login.render(loginForm));
    }
	
	// como fazer contagem das sessions? um atributo static int que incrementa a cada autenticacao?
	
	@Transactional
	public static Result logout() {
		session().clear();
		flash("success", "Você saiu do sistema!");
		return ok(login.render(Form.form(Usuario.class)));
	}
    
	@Transactional
	public static Result authenticate() {

		Form<Usuario> usuarioForm = loginForm.bindFromRequest();
		

		if (usuarioForm.hasErrors()) {
			flash("fail", "Erro no formulário.");
        	return badRequest(login.render(loginForm));						
		}else{
			Usuario usuario = usuarioForm.get();
			String email = usuario.getEmail();
			String senha = usuario.getSenha();

	        if (!validate(email, senha)) {
	        	flash("fail", "Email ou Senha inválida");
	        	return badRequest(login.render(loginForm));
	        } else {
	        	Participante user = (Participante) dao.findByAttributeName(
	        			"Participante", "email", usuario.getEmail()).get(0);
	            session().clear();
	            session("email", user.getEmail());
	            return redirect(routes.Application.index());
	        }
		}
		
    }
	
	private static boolean validate(String email, String senha) {
		List<Usuario> usuarios = dao.findByAttributeName("Usuario", "email", email);
		if (usuarios == null || usuarios.isEmpty()) {
			return false;
		}
		if (!usuarios.get(0).getSenha().equals(senha)) {
			return false;
		}
		return true;
	}
}