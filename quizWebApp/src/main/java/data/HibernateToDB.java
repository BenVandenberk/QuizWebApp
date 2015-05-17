package data;

import java.util.List;

import model.Deelname;
import model.Deelnemer;
import model.Gebruiker;
import model.VragenReeks;
import model.vraag.Vraag;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

class HibernateToDB {

	private static HibernateToDB uniekeInstantie;
	private SessionFactory sessionFactory;
	Session session;

	private HibernateToDB() {
		Configuration configuration = new Configuration();
		configuration.configure();
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(ssrb.build());
		session = null;
	}

	protected static HibernateToDB getUniekeInstantie() {
		if (uniekeInstantie == null) {
			uniekeInstantie = new HibernateToDB();
		}
		return uniekeInstantie;
	}

	protected Gebruiker getGebruiker(String gebruikersNaam) {
		String hql = "FROM Gebruiker G WHERE G.gebruikersNaam='" + gebruikersNaam + "'";
		session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		Gebruiker gebruiker = (Gebruiker) query.uniqueResult();
		session.close();
		return gebruiker;
	}

	protected void saveDeelnemer(Deelnemer deelnemer) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(deelnemer);
		session.getTransaction().commit();
		session.close();
	}

	protected void saveDeelname(Deelname deelname) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(deelname);
		session.getTransaction().commit();
		session.close();
	}

	protected void saveGebruiker(Gebruiker gebruiker) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(gebruiker);
		session.getTransaction().commit();
		session.close();
	}

	protected void saveVragenReeks(VragenReeks vragenReeks) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(vragenReeks);
		session.getTransaction().commit();
		session.close();
	}

	protected void saveVragen(List<Vraag> vragen) {
		session = sessionFactory.openSession();
		session.beginTransaction();

		for (Vraag v : vragen) {
			session.persist(v);
		}

		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	protected List<VragenReeks> getVragenReeksen() {
		session = sessionFactory.openSession();
		String hql = "FROM VragenReeks";
		Query query = session.createQuery(hql);
		List<VragenReeks> vragenReeksen = query.list();
		session.close();
		return vragenReeksen;
	}

	@SuppressWarnings("unchecked")
	protected List<Deelnemer> getDeelnemers() {
		session = sessionFactory.openSession();
		String hql = "FROM Deelnemer";
		Query query = session.createQuery(hql);
		List<Deelnemer> deelnemers = query.list();
		for (Deelnemer deelnemer : deelnemers) {
			Hibernate.initialize(deelnemer.getTeMakenReeksen());
		}
		session.close();
		return deelnemers;
	}

	protected VragenReeks getVragenReeks(int id) {
		session = sessionFactory.openSession();
		VragenReeks result = (VragenReeks) session.get(VragenReeks.class, id);
		session.close();
		return result;
	}

	protected Deelname getDeelname(int id) {
		Deelname result;
		session = sessionFactory.openSession();
		result = (Deelname) session.get(Deelname.class, id);
		Hibernate.initialize(result.getAntwoorden());
		session.close();
		return result;
	}
}
