package controllers;
 
import static play.data.Form.form;
import models.Participante;
import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
 
public class Registro extends Controller {
       
        private static GenericDAO dao = new GenericDAOImpl();
        static Form<Usuario> registroForm = form(Usuario.class).bindFromRequest();
 
        @Transactional
    public static Result show() {
        return ok(registro.render(registroForm));
    }
       
        @Transactional
        public static Result registrar() {
            Usuario usuario = registroForm.get();
            if (registroForm.hasErrors() || !validate(usuario)) {
            	flash("fail", "Email já está em uso");
                return badRequest(registro.render(registroForm));
            } else {
                dao.persist(usuario);
                dao.flush();
                return redirect(routes.Login.show());
            }
        }
       
        private static boolean validate(Usuario usuario) {
                if(!emailEmUso(usuario.getEmail())){
                        return true;
                }
                return false;
        }
       
        private static boolean emailEmUso(String email){
               /* Participante participante = (Participante) dao.findByAttributeName(
                        "Participante", "email", usuario.getEmail()).get(0);
                if(participante.getEmail().equals(email)){
                        return true;
                }*/
                return false;
        }
}