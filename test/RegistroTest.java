import static org.junit.Assert.*;

import java.util.List;

import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.PessoaInvalidaException;

import org.junit.Assert;
import org.junit.Test;


public class RegistroTest extends AbstractTest {

	GenericDAO dao = new GenericDAOImpl();
	
	@Test
	public void deveValidarUmEmailParaRegistro() {
		
		//testa se o banco de dados esta vazio
		List<Participante> allParticipantes = dao.findAllByClassName("Participante");
		Assert.assertTrue(allParticipantes.size() == 0);
		Participante p;
		
		
		//entao criamos o participante
		try {
			p = new Participante("Gustavo", "guga@gmail.com", "guga");
			
			//vamos tentar criar um participante com email guga@gmail.com, ele deve validar
			Assert.assertTrue(validate(p));
			if(validate(p)){
				dao.persist(p);
				dao.flush();
			}
		} catch (PessoaInvalidaException e) {
			fail();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// deve haver apenas 1 participante
		allParticipantes = dao.findAllByClassName("Participante");
		Assert.assertTrue(allParticipantes.size() == 1);
		
		//testa se registrou os dados corretamente
		Participante p2 = (Participante) dao.findByAttributeName("Participante", "email", "guga@gmail.com").get(0);
		Assert.assertTrue(p2.getEmail().equals("guga@gmail.com"));
		Assert.assertTrue(p2.getNome().equals("Gustavo"));
		Assert.assertTrue(p2.getSenha().equals("guga"));
		
		//ao tentar criar outra conta com mesmo email, deve falhar
		try {
			p = new Participante("Gustavo", "guga@gmail.com", "guga");
			Assert.assertFalse(validate(p));
			if(validate(p)){
				fail();
				dao.persist(p);
				dao.flush();
			}
		} catch (PessoaInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private boolean validate(Participante participante) {
        if(!emailEmUso(participante.getEmail())){
                return true;
        }
        else{
        	return false;
        }
}

	private boolean emailEmUso(String email){
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
