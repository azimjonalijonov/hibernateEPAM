package org.example.trainer;

import org.example.interfaces.BaseDAO;
import org.example.user.User;
import org.example.user.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainerDAO implements BaseDAO<Trainer> {

	private final UserDAO userDAO;

	private final SessionFactory sessionFactory;

	//
	// public TrainerDAO(UserDAO userDAO, SessionFactory sessionFactory) {
	// this.userDAO = userDAO;
	// this.sessionFactory = sessionFactory;
	// }
	//
	public TrainerDAO(UserDAO userDAO, SessionFactory sessionFactory) {
		this.userDAO = userDAO;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Trainer> readAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Trainer ", Trainer.class).list();
		}
	}

	@Override
	public Trainer readById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Trainer.class, id);
		}
	}

	@Override
	public Trainer createOrUpdate(Trainer entity) {
		try (Session session = sessionFactory.openSession()) {
			session.saveOrUpdate(entity);
		}
		return entity;
	}

	@Override
	public boolean deleteById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Trainer training = session.get(Trainer.class, id);
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

	public Trainer readByUsername(String username) {
		Trainer trainer;
		User user = userDAO.readByUsername(username);

		try (Session session = sessionFactory.openSession()) {
			trainer = session.createQuery("FROM Trainer WHERE user = :user", Trainer.class)
				.setParameter("user", user)
				.uniqueResult();
		}
		return trainer;

	}

	public String updatePassword(String password, Long id) {
		Trainer trainer = readById(id);
		User user = trainer.getUser();
		user.setPassword(password);
		userDAO.createOrUpdateForPassword(user);
		return "Updated password";
	}

	public String changeActivation(Boolean bool, Long id) {
		Trainer trainer = readById(id);
		User user = trainer.getUser();
		user.setActive(bool);
		userDAO.createOrUpdate(user);
		return "Updated activation for trainer";
	}

	public List<Trainer> findActiveTrainersWithNoAssignees() {
		try (Session session = sessionFactory.openSession()) {
			return session
				.createQuery(
						"SELECT DISTINCT t " + "FROM Trainer t " + "WHERE t.user.isActive = true " + "AND NOT EXISTS ("
								+ "   SELECT 1 FROM TraineeTrainer tt " + "   WHERE tt.trainer = t" + ")",
						Trainer.class)
				.getResultList();
		}
	}

}
