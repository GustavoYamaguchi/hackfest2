import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import controllers.Login;
import models.*;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;

public class LoginTest extends AbstractTest {

	GenericDAO dao = new GenericDAOImpl();
	
	@Test
	public void deveAutenticarOParticipante() {
			List<Tema> temas = new ArrayList<>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			Calendar calendar;
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DAY_OF_WEEK, 3);

			//cria evento
			Evento e;
			try {
				e = new Evento("Luta de robos", "Robos lutando", calendar.getTime(), temas);
				dao.persist(e);
				dao.merge(e);
				dao.flush();
			} catch (EventoInvalidoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//cria participante
			Participante p;
			try {
				p = new Participante("Gustavo", "guga@gmail.com", "guga");
				dao.persist(p);
				dao.flush();
			} catch (PessoaInvalidaException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<Participante> participantes = dao.findByAttributeName("Participante", "email", "guga@gmail.com");
			Assert.assertTrue(participantes.size() == 1);
			Participante p2 = participantes.get(0);
			Assert.assertTrue(p2.getEmail().equals("guga@gmail.com"));
			Assert.assertTrue(p2.getSenha().equals("guga"));
			Assert.assertTrue(p2.getNome().equals("Gustavo"));
		
			//testa login
			Assert.assertTrue(validate("guga@gmail.com", "guga"));
			Assert.assertFalse(validate("a@a.com", "a"));
			Assert.assertFalse(validate("guga@gmail.com", "guga123"));
		
	}

	private boolean validate(String email, String senha){
		List<Participante> participantes = dao.findByAttributeName("Participante", "email", email);
		if (participantes == null || participantes.isEmpty()) {
			return false;
		}
		Participante participante = participantes.get(0);
		if (!participante.getSenha().equals(senha)) {
			return false;
		}
		return true;
	}
}
