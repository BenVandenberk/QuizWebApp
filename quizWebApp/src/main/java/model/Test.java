package model;

import data.DBFacade;

public class Test {

	public static void main(String[] args) {
		// Deelnemer deelnemer = new Deelnemer("testGebruiker");
		//
		// VragenReeks vragenReeks = new VragenReeks();
		// vragenReeks.setThema(Thema.Biologie);
		// vragenReeks.setNaam("De boerderij");
		//
		// VragenReeks vragenReeksVoorgaand = new VragenReeks();
		// vragenReeksVoorgaand.setThema(Thema.Rekenen);
		// vragenReeksVoorgaand.setNaam("Priemgetallen");
		//
		// vragenReeks.addVoorgaandeVragenReeks(vragenReeksVoorgaand);
		//
		// Vraag kVraag = new KlassiekeVraag("TestVraag", "TestAntwoord");
		// String[] antwoord = { "a" };
		// ArrayList<String> keuzes = new ArrayList<String>();
		// keuzes.add("a");
		// keuzes.add("b");
		// keuzes.add("c");
		// Vraag mcVraag = new MultipleChoiceVraag("a, b of c?", keuzes, antwoord);
		// Vraag nVraag = new NumeriekeVraag("1 + 1?", 2);
		// HashMap<String, String> dndAntwoord = new HashMap<String, String>();
		// dndAntwoord.put("olifant", "steppe");
		// dndAntwoord.put("koe", "boerderij");
		// Vraag dndVraag = new DragAndDropVraag("Sleep de dieren naar het juiste veld", dndAntwoord);
		// vragenReeks.addVraag(mcVraag);
		// vragenReeks.addVraag(dndVraag);
		// vragenReeks.addVraag(nVraag);
		// vragenReeks.addVraag(kVraag);
		//
		// Deelname huidigeDeelname = deelnemer.neemDeel(vragenReeks);
		// deelnemer.addToDo(vragenReeks);
		//
		// String[] gegAntwoord = { "a" };
		// huidigeDeelname.antwoord(vragenReeks.getReeksVraag(4), new KlassiekAntwoord("TestAntwoord"));
		// huidigeDeelname.antwoord(vragenReeks.getReeksVraag(1), new MultipleChoiceAntwoord(gegAntwoord));
		// huidigeDeelname.antwoord(vragenReeks.getReeksVraag(2), new DragAndDropAntwoord(dndAntwoord));
		// huidigeDeelname.antwoord(vragenReeks.getReeksVraag(3), new NumeriekAntwoord(2));
		//
		// Gebruiker g = new Gebruiker("Ben", "pw");
		//
		// Configuration configuration = new Configuration();
		// configuration.configure();
		// StandardServiceRegistryBuilder ssrb = new
		// StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		// SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
		//
		// Session session = sessionFactory.openSession();
		// session.beginTransaction();
		// session.persist(kVraag);
		// session.persist(nVraag);
		// session.persist(dndVraag);
		// session.persist(mcVraag);
		// session.persist(vragenReeksVoorgaand);
		// session.persist(deelnemer);
		// session.persist(vragenReeks);
		// session.persist(g);
		// session.getTransaction().commit();
		// session.close();

		DBFacade dbf = DBFacade.getUniekeInstantie();
		Deelnemer ben = dbf.login("Ben", "pd");
		System.out.println(ben.getGebruikersNaam());
	}
}
