package data;

import java.util.List;

import model.Deelname;
import model.Deelnemer;
import model.Gebruiker;
import model.Rapport;
import model.VragenReeks;
import model.vraag.Vraag;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

class HibernateToDB implements IDBCore {

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

	@Override
	public Gebruiker getGebruiker(String gebruikersNaam) {
		String hql = "FROM Gebruiker G WHERE G.gebruikersNaam='" + gebruikersNaam + "'";
		session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		Gebruiker gebruiker = (Gebruiker) query.uniqueResult();
		if (gebruiker != null) {
			Hibernate.initialize(gebruiker.getDeelnemer());
			Hibernate.initialize(gebruiker.getDeelnemer().getTeMakenReeksen());
			for (VragenReeks vr : gebruiker.getDeelnemer().getTeMakenReeksen()) {
				Hibernate.initialize(vr.getVoorgaandeVragenReeksen());
			}
			for (Deelname dlnm : gebruiker.getDeelnemer().getDeelnames()) {
				Hibernate.initialize(dlnm.getAntwoorden());
			}
		}
		session.close();
		return gebruiker;
	}

	@Override
	public void saveDeelnemer(Deelnemer deelnemer) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(deelnemer);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void saveDeelname(Deelname deelname) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(deelname);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void saveRapport(Rapport rapport) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(rapport);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void saveGebruiker(Gebruiker gebruiker) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(gebruiker);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void saveVragenReeks(VragenReeks vragenReeks) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(vragenReeks);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void saveVragen(List<Vraag> vragen) {
		session = sessionFactory.openSession();
		session.beginTransaction();

		for (Vraag v : vragen) {
			session.persist(v);
		}

		session.getTransaction().commit();
		session.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VragenReeks> getVragenReeksen() {
		session = sessionFactory.openSession();
		String hql = "FROM VragenReeks";
		Query query = session.createQuery(hql);
		List<VragenReeks> vragenReeksen = query.list();
		for (VragenReeks vr : vragenReeksen) {
			Hibernate.initialize(vr.getVoorgaandeVragenReeksen());
		}
		session.close();
		return vragenReeksen;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Deelnemer> getDeelnemers() {
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

	@Override
	@SuppressWarnings("unchecked")
	public List<Rapport> getRapporten() {
		session = sessionFactory.openSession();
		String hql = "FROM Rapport";
		Query query = session.createQuery(hql);
		List<Rapport> rapporten = query.list();
		session.close();
		return rapporten;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Deelname> getDeelnames() {
		session = sessionFactory.openSession();
		String hql = "FROM Deelname";
		Query query = session.createQuery(hql);
		List<Deelname> deelnames = query.list();
		for (Deelname deelname : deelnames) {
			Hibernate.initialize(deelname.getDeelnemer());
			Hibernate.initialize(deelname.getAntwoorden());
		}
		session.close();
		return deelnames;
	}

	@Override
	public VragenReeks getVragenReeks(int id) {
		session = sessionFactory.openSession();
		VragenReeks result = (VragenReeks) session.get(VragenReeks.class, id);
		session.close();
		return result;
	}

	@Override
	public Deelname getDeelname(int id) {
		Deelname result;
		session = sessionFactory.openSession();
		result = (Deelname) session.get(Deelname.class, id);
		if (result != null) {
			Hibernate.initialize(result.getAntwoorden());
		}
		session.close();
		return result;
	}

	@Override
	public Rapport getRapport(int id) {
		session = sessionFactory.openSession();
		Rapport result = (Rapport) session.get(Rapport.class, id);
		Hibernate.initialize(result.getDeelnames());
		for (Deelname d : result.getDeelnames()) {
			Hibernate.initialize(d.getAntwoorden());
		}
		session.close();
		return result;
	}
}
