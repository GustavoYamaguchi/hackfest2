package controllers;
 
import static play.data.Form.form;
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
        static Form<Participante> registroForm = form(Participante.class).bindFromRequest();
 
        @Transactional
        public static Result show() {
        	return ok(registro.render(registroForm));
        }
       
        @Transactional
        public static Result registrar() {
            Participante participante = registroForm.get();
            if (registroForm.hasErrors() || !validate(participante)) {
            	flash("fail", "Email já está em uso");
                return badRequest(registro.render(registroForm));
            } else {
                dao.persist(participante);
                dao.flush();
                return redirect(routes.Login.show());
            }
        }
       
        private static boolean validate(Participante participante) {
                if(!emailEmUso(participante.getEmail())){
                        return true;
                }
                return false;
        }
       
        private static boolean emailEmUso(String email){
                Participante participante = (Participante) dao.findByAttributeName(
                        "Participante", "email", email).get(0);
                if(participante.getEmail().equals(email)){
                        return true;
                }
                return false;
        }
}