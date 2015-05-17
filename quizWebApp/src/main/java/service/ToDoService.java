package service;

import java.util.List;

import model.Deelnemer;
import model.VragenReeks;
import context.ToDoContext;
import data.DBFacade;

public class ToDoService {

	private static ToDoService uniekeInstantie;
	private DBFacade dbFacade;

	private ToDoService() {
		dbFacade = DBFacade.getUniekeInstantie();
	}

	public static ToDoService getUniekeInstantie() {
		if (uniekeInstantie == null) {
			uniekeInstantie = new ToDoService();
		}
		return uniekeInstantie;
	}

	public ToDoContext getToDoContext() {
		List<Deelnemer> deelnemers = dbFacade.getDeelnemers();
		List<VragenReeks> vragenReeksen = dbFacade.getVragenReeksen();

		return new ToDoContext(deelnemers, vragenReeksen);
	}

}
