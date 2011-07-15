package info.typea.sample.restservice.dao;

import info.typea.sample.restservice.entity.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityDaoImpl implements CityDao {
	private static final String PERSISTENCE_UNIT = "toursdb_persistence_unit";
	private static final Logger logger = LoggerFactory.getLogger(CityDaoImpl.class);
	
	/**
	 * テーブル定義が、自動採番でないため、最大値＋1を取得
	 * 自動採番の場合、Entity に @GeneratedValue(strategy = GenerationType.IDENTITY) を指定
	 * @return
	 */
	public int getNextCityId() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		Integer cityId = (Integer) em.createQuery("select max(c.cityId) from City c").getSingleResult();
		
		return cityId.intValue()  + 1;
	}
	
	public City insertCity(City city) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		
		try {
			
			EntityTransaction trn = em.getTransaction();
			trn.begin();
			int cityId = getNextCityId();
			city.setCityId(new Integer(cityId));
			
			em.persist(city);
			
			trn.commit();
		
		} catch (Exception e) {
			logger.error("failur insert to city :", e);
			throw new RuntimeException(e);
		} finally {
			em.close();
		}
		return city;
	}
	
	public City findById(String cityId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("select c from City c where c.cityId = :cityId");
		query.setParameter("cityId", Integer.parseInt(cityId));

		return (City)query.getSingleResult();
	}

	public boolean updateCity(City dstCity) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		
		try {
			
			EntityTransaction trn = em.getTransaction();
			trn.begin();

			// MANAGED 状態のオブジェクトを取得し更新
			City city = em.find(City.class, dstCity.getCityId());
			
			city.setCityName(dstCity.getCityName());
			city.setCountry(dstCity.getCountry());
			city.setLanguage(dstCity.getLanguage());
			city.setAirport(dstCity.getAirport());
			
			trn.commit();
					
		} catch (Exception e) {
			logger.error("failur delete city :", e);
			throw new RuntimeException(e);
		} finally {
			em.close();
		}
		return true;
	}
	
	public boolean deleteById(String cityId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();
		
		try {
			
			EntityTransaction trn = em.getTransaction();
			trn.begin();

			// MANAGED 状態のオブジェクトを取得し削除
			City city = em.find(City.class, new Integer(cityId));
			em.remove(city);

			trn.commit();
					
		} catch (Exception e) {
			logger.error("failur delete city :", e);
			throw new RuntimeException(e);
		} finally {
			em.close();
		}
		return true;
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
		
		// 動作確認のためのやっつけコード
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("c.cityId",   						cityId);
		parm.put("c.cityName", 						cityName);
		parm.put("c.country",  						country);
		parm.put("c.language", 						language);
		parm.put("c.countryBean.countryIsoCode", 	countryIsoCode);
		parm.put("c.countryBean.region", 			region);
		
		logger.info("*** CityDao find parameters ***");
		for (String key : parm.keySet()) {
			logger.info(key + " = " + parm.get(key));
		}
		
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
