package org.example.traineeTrainers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TraineeTrainerDAO {

	private final SessionFactory sessionFactory;

	public TraineeTrainerDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public TraineeTrainer createOrUpdate(TraineeTrainer traineeTrainer) {
		try (Session session = sessionFactory.openSession()) {
			session.saveOrUpdate(traineeTrainer);
		}
		return traineeTrainer;
	}

}
