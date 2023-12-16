package org.example.training;

import org.example.interfaces.BaseDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

public class TrainingDAO implements BaseDAO<Training> {

	private final SessionFactory sessionFactory;

	public TrainingDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Training> readAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Training ", Training.class).list();
		}
	}

	@Override
	public Training readById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Training.class, id);
		}
	}

	@Override
	public Training createOrUpdate(Training entity) {
		try (Session session = sessionFactory.openSession()) {
			session.saveOrUpdate(entity);
		}
		return entity;
	}

	@Override
	public boolean deleteById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Training training = session.get(Training.class, id);
			if (training != null) {
				session.remove(training);
				return true;
			}
			session.getTransaction().commit();
			return false;
		}
	}

	@Override
	public boolean existById(Long id) {
		return !readById(id).equals(null);
	}

}
