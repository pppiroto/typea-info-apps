package info.typea.sample.springmvc.dao;

import info.typea.sample.springmvc.entity.Menu;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MenuDaoImpl implements MenuDao {

	public List<Menu> findAll() {
		EntityManagerFactory emf 
			= Persistence.createEntityManagerFactory("settingsdb_persistence_unit");
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<Menu> list = em.createQuery("select m from Menu m").getResultList();
		return list;
	}
}
