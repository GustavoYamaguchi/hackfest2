package controllers;
 
import static play.data.Form.form;

import java.util.List;

import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
 
public class Registro extends Controller {
       
        private static GenericDAO dao = new GenericDAOImpl();
        static Form<Participante> registroForm = Form.form(Participante.class);
 
        @Transactional
        public static Result show() {
        	return ok(registro.render(registroForm));
        }
       
        @Transactional
        public static Result registrar() {
        	Form<Participante> registroPessoa = registroForm.bindFromRequest();
            Participante participante;
            
            if (registroForm.hasErrors()) {
            	flash("fail", "Email já está em uso");
                return badRequest(registro.render(registroForm));
            }/*
            else if(!validate(participante)){
            	flash("fail", "Email já está em uso");
                return badRequest(registroPessoa.render(registroForm));
            }*/else {
            	participante = registroPessoa.bindFromRequest().get();
                dao.persist(participante);
                dao.flush();
                flash("success", "Email cadastrado com sucesso.");
                return redirect(routes.Application.index());
            }
        }
       
       /* private static boolean validate(Participante participante) {
                if(!emailEmUso(participante.getEmail())){
                        return true;
                }
                else{
                	return false;
                }
        }*/
       
        private static boolean emailEmUso(String email){
                List<Participante> participantes = dao.findByAttributeName(
                        "Participante", "email", email);
                if(participantes.size() == 0 | participantes ==null){
                	return false;
                }
                Participante participante = (Participante) participantes.get(0);
                if(participante.getEmail().equals(email)){
                        return true;
                }else{
                    return false;                	
                }
        }
}