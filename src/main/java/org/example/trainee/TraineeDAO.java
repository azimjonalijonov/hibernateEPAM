package org.example.trainee;

import org.example.interfaces.BaseDAO;

import org.example.user.User;
import org.example.user.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

public class TraineeDAO implements BaseDAO<Trainee> {

	@Autowired
	private UserDAO userDAO;

	private final SessionFactory sessionFactory;

	public TraineeDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Trainee> readAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Trainee ", Trainee.class).list();
		}
	}

	@Override
	public Trainee readById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Trainee.class, id);
		}
	}

	@Override
	public Trainee createOrUpdate(Trainee entity) {
		try (Session session = sessionFactory.openSession()) {
			session.saveOrUpdate(entity);

		}
		return entity;
	}

	@Override
	public boolean deleteById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Trainee training = session.get(Trainee.class, id);
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

	public Trainee readByUsername(String username) {
		Trainee trainee;
		User user = userDAO.readByUsername(username);
		try (Session session = sessionFactory.openSession()) {
			trainee = session.createQuery("FROM Trainee WHERE user = :user", Trainee.class)
				.setParameter("user", user)
				.uniqueResult();
		}
		return trainee;

	}

	public String updatePassword(String password, Long id) {
		Trainee trainee = readById(id);
		User user = trainee.getUser();
		user.setPassword(password);
		userDAO.createOrUpdateForPassword(user);
		return "Updated password to  " + user.getPassword() + " for user+ " + user.getUsername();
	}

	public String changeActivation(Boolean bool, Long id) {
		Trainee trainee = readById(id);
		User user = trainee.getUser();
		user.setActive(bool);
		userDAO.createOrUpdate(user);
		return "Updated activation";
	}

	public String deleteByUsername(String username) {
		Trainee trainee;
		User user = userDAO.readByUsername(username);

		try (Session session = sessionFactory.openSession()) {
			trainee = session.createQuery("FROM Trainee WHERE user = :user", Trainee.class)
				.setParameter("user", user)
				.uniqueResult();
		}
		deleteById(trainee.getId());
		return "trainee with username " + username + " is deleted";
	}

}
