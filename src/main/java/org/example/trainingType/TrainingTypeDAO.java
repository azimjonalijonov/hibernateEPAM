package org.example.trainingType;

import org.example.interfaces.BaseDAO;
;
import org.example.trainer.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
 import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public class TrainingTypeDAO {

	private final SessionFactory sessionFactory;


	public TrainingTypeDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<TrainingType> readAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM TrainingType ", TrainingType.class).list();
		}	}

	public TrainingType readById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(TrainingType.class, id);
		}
	}







	public boolean existById(Long id) {
		return !readById(id).equals(null);
	}


	public TrainingType createOrUpdate(TrainingType trainingType) {
 			try (Session session = sessionFactory.openSession()) {
				session.saveOrUpdate(trainingType);
			}
			return trainingType;
		}

}
