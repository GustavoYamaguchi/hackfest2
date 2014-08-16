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

	@Test
	public void test() throws PessoaInvalidaException, EventoInvalidoException {
			List<Tema> temas = new ArrayList<>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			Calendar calendar;
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DAY_OF_WEEK, 3);

			GenericDAO dao = new GenericDAOImpl();
			//cria evento
			Evento e = new Evento("Luta de robos", "Robos lutando", calendar.getTime(), temas);
			dao.persist(e);
			dao.merge(e);
			dao.flush();
			
			//cria participante
			Participante p = new Participante("Gustavo", "guga@gmail.com", "guga", e);
			dao.persist(p);
			dao.flush();
			List<Participante> participantes = dao.findByAttributeName("Participante", "email", "guga@gmail.com");
			Assert.assertTrue(participantes.size() == 1);
			Participante p2 = participantes.get(0);
			Assert.assertTrue(p2.getEmail().equals("guga@gmail.com"));
			Assert.assertTrue(p2.getSenha().equals("guga"));
			Assert.assertTrue(p2.getNome().equals("Gustavo"));
		
			//testa login
			List<Participante> participantess = dao.findByAttributeName("Participante", "email", "guga@gmail.com");
			Participante participante = participantess.get(0);
			boolean validaAutenticacao;
				if (participantes == null || participantes.isEmpty()) {
					validaAutenticacao = false;
				}
				else if (!participante.getSenha().equals("guga")) {
					validaAutenticacao = false;
				}
				else validaAutenticacao= true;
			Assert.assertTrue(validaAutenticacao);
		
	}

}
