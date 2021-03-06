package controllers;

import models.Evento;
import models.Participante;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	
	private static boolean criouEventosFake = false;
	private static GenericDAO dao = new GenericDAOImpl();
	private static Participante participante;
	/*private static final int TRES = 3, DATA_SETE = 7, DATA_TRES = 3, DATA_UM = 1, DATA_DOZE = 12, DATA_DEZESSETE = 17, 
			DATA_CINCO = 5, DATA_VINTE_UM = 21, DATA_QUINZE = 15, DATA_OITO = 8;*/

	@Transactional
    public static Result index(){
		if (!criouEventosFake){
		/*	List<Evento> eventos = criarEventosFakes();
			criarParticipacoesFake(eventos);*/

			criouEventosFake = true;
		}
		if(session().get("email") == null | dao.findByAttributeName("Participante","email", session().get("email")).size() == 0){
			return redirect(routes.Login.show());
		}
		participante = (Participante) dao.findByAttributeName("Participante","email", session().get("email")).get(0);
        return ok(index.render(participante));
    }

	public static GenericDAO getDao(){
		return dao;
	}

	/*private static List<Evento> criarEventosFakes() {
		try {
			List<Evento> eventos = new ArrayList<Evento>();
			Evento evento;
			Calendar calendar;
	
			List<Tema> temas = new ArrayList<Tema>();
			
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_SETE);
			
			evento = new Evento("Python na mente e coração", "Neste evento iremos debater e propor soluções para novas releases.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
			
			temas = new ArrayList<Tema>();
			temas.add(Tema.ARDUINO);
			temas.add(Tema.ELETRONICA);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_TRES);

			evento = new Evento("Luta de robôs", "Traga seu robô feito em arduino e traga para competir com outros.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, DATA_UM);

			evento = new Evento("IV Olímpiadas de programação da UFCG", "Traga sua equipe e venha competir nessa maratona de programação.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_DOZE);

			evento = new Evento("II Encontro para programadores de Python", "O encontro contará com a participação de um de seus fundadores, inúmeras palestras e maratonas. Não percam!!", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.DESAFIOS);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DAY_OF_WEEK, DATA_TRES);

			evento = new Evento("III Semana da Computação Verde", "Exiba sua proposta para uma computação mais verde e concorra a diversos prêmios", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.WEB);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_DEZESSETE);

			evento = new Evento("Web em foco", "Este evento contará com a participação de um dos fundadores da Web, e juntos iremos compartilhar diversas dicas e boas práticas nessa vasta área.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.ELETRONICA);
			temas.add(Tema.ARDUINO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_CINCO);

			evento = new Evento("Minicurso Arduino", "Evento destinado a alunos de LOAC, caso sobre vagas iremos disponibilizar em breve", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.ELETRONICA);
			temas.add(Tema.ARDUINO);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_VINTE_UM);

			evento = new Evento("Curto circuito", "Evento sobre circuitos eletrônicos, venha dar curto em seus circuitos também!!", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
	
			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_QUINZE);

			evento = new Evento("VI Encontro de Docentes de CC", "Evento para debatermos propostas e soluções para os problemas enfrentados pelos alunos de CC.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.DESAFIOS);
			
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_OITO);

			evento = new Evento("Café com Java", "Curso destinado apenas a alunos cursando a disciplina LP2.", calendar.getTime(), temas);
			eventos.add(evento);
			criarEvento(evento);
			
			return eventos;
		} catch (EventoInvalidoException e) {
			return null;
		}
	}
	
	private static void criarParticipacoesFake(List<Evento> eventos) {
		Random rnd = new Random();
		try {
			criarParticipacao(new Participante("Alberto Leça", "alberto_leca@mail.com", "a", eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Alberto Leça", "alberto_leca@mail.com", "a",eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Alberto Leça", "alberto_leca@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Belmifer Linares", "belmifer_linares@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Belmifer Linares", "belmifer_linares@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Célia Rúa", "celia_rua@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Deolindo Castello Branco", "deolindo_castello@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
            criarParticipacao(new Participante("Doroteia Pasos", "doroteia_passos@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Eugénio Palhares", "eugenio_palhares@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Fausto Furtado", "fausto_furtado@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Filipa Leiria", "filipa_leiria@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Leonilde Figueiredo", "leonilde_figueiredo@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Pascoal Caldeira", "pascoal_caldeira@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Paula Lousado", "paula_lousado@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Quitério Galindo","quiterio_galindo@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Rosa Varejão", "rosa_varejao@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Sonia Gabeira", "sonia_gabeira@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Érico Albuquerque", "erico_albuquerque@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Érico Albuquerque", "erico_albuquerque@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
			criarParticipacao(new Participante("Tairine Reis", "tairine_reis@mail.com", "a",  eventos.get(rnd.nextInt(TRES))));
		} catch (PessoaInvalidaException _) { }
	}*/
	
	@Transactional
	private static void criarEvento(Evento evento) {
		dao.persist(evento);
		dao.merge(evento);
		dao.flush();
	}
	
	@Transactional
	private static void criarParticipacao(Participante participante) {
		dao.persist(participante);
		dao.flush();
	}

}
