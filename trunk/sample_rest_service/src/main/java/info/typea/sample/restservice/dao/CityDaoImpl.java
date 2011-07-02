package info.typea.sample.restservice.dao;

import info.typea.sample.restservice.entity.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CityDaoImpl implements CityDao {
	private static final String PERSISTENCE_UNIT = "toursdb_persistence_unit";
	//private static final Logger logger = LoggerFactory.getLogger(CityDaoImpl.class);
	
	public City findById(String cityId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("select c from City c where c.cityId = :cityId");
		query.setParameter("cityId", Integer.parseInt(cityId));

		return (City)query.getSingleResult();
	}

	public List<City> findAll() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<City> list = em.createQuery("select c from City c").getResultList();
		
		return list;
	}

	public List<City> find(
			Integer cityId, String cityName, String country,
			String language, String countryIsoCode, String region) {
		
		// ����m�F�̂��߂̂�����R�[�h
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("c.cityId",   						cityId);
		parm.put("c.cityName", 						cityName);
		parm.put("c.country",  						country);
		parm.put("c.language", 						language);
		parm.put("c.countryBean.countryIsoCode", 	countryIsoCode);
		parm.put("c.countryBean.region", 			region);
		
		StringBuffer qbuf = new StringBuffer("select c from City c");
		boolean isFirst 	= true;
		boolean isInputted 	= false;
		int spos = 0;
		for (String key : parm.keySet()) {
			Object value = parm.get(key);
			isInputted = false;
			
			if (value instanceof String) {
				isInputted = (value != null && value.toString().trim().length() > 0);
			} else {
				isInputted = (value != null);
			}
			if (isInputted) {
				if (isFirst) {
					qbuf.append(" where ");
				} else {
					qbuf.append(" and ");
				}
				spos = key.lastIndexOf('.') + 1;
				qbuf.append(key + "=:" + key.substring(spos));
				isFirst = false;
			} 
		}
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery(qbuf.toString());
		for (String key : parm.keySet()) {
			Object value = parm.get(key);
			isInputted = false;
			
			if (value instanceof String) {
				isInputted = (value != null && value.toString().trim().length() > 0);
			} else {
				isInputted = (value != null);
			}
			if (isInputted) {
				spos = key.lastIndexOf('.') + 1;
				query.setParameter(key.substring(spos), value);
			}
		}
		
		@SuppressWarnings("unchecked")
		List<City> list = query.getResultList();
		
		return list;
	}
	
	public List<String> getCityNamesSuggest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<String> list = em.createQuery("select distinct c.cityName from City c order by c.cityName").getResultList();
		
		return list;
	}
	public List<String> getCountrySuggest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<String> list = em.createQuery("select distinct c.country from City c order by c.country").getResultList();
		
		return list;
	}

	public List<String> getLanguageSuggest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<String> list = em.createQuery("select distinct c.language from City c order by c.language").getResultList();
		
		return list;
	}

	public List<String> getCountryIsoCodeSuggest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<String> list = em.createQuery("select distinct c.countryIsoCode from Country c order by c.countryIsoCode").getResultList();
		
		return list;
	}

	public List<String> getRegionSuggest() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("unchecked")
		List<String> list = em.createQuery("select distinct c.region from Country c order by c.region").getResultList();
		
		return list;
	}

}