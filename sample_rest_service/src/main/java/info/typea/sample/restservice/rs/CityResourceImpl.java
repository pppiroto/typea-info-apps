package info.typea.sample.restservice.rs;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.ws.rs.Encoded;
import javax.ws.rs.WebApplicationException;

import info.typea.sample.restservice.dao.CityDao;
import info.typea.sample.restservice.dto.CompleteList;
import info.typea.sample.restservice.entity.Cities;
import info.typea.sample.restservice.entity.City;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityResourceImpl implements CityResource {
	private static final Logger logger = LoggerFactory.getLogger(CityResourceImpl.class);
	
	private CityDao cityDao;
	
	public CityDao getCityDao() {
		return cityDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}
	
	
	
	public City insertCity(City city) {
		try {
			return cityDao.insertCity(city);
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
		
	}
	
	public void updateCity(City city) {
		try {
			cityDao.updateCity(city);
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
		return;
	}
	
	public void deleteCity(String cityId) {
		try {
			cityDao.deleteById(cityId);
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
		return;
	}
	
	public City getCity(String cityId) {
		
		return cityDao.findById(cityId);
	}

	public Cities getCities() {
		
		return new Cities(cityDao.findAll());
	}

	public Cities find(	String cityId, 
						String cityName, 
						String country,
						String language, 
						String countryIsoCode, 
						String region) {
		
		// @Encoded å¯Ç©Ç»Ç¢ÅH
		try {
			cityName = URLDecoder.decode(cityName,"UTF-8");
			country = URLDecoder.decode(country,"UTF-8");
			language = URLDecoder.decode(language,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("cityId=" + cityId);
		logger.info("cityName=" + cityName);
		logger.info("country=" + country);
		logger.info("language=" + language);
		logger.info("countryIsoCode=" + countryIsoCode);
		logger.info("region=" + region);
		
		Integer intCityId = null;
		try {
			intCityId = Integer.valueOf(cityId);
		} catch (NumberFormatException nfe) {}
		
		return new Cities(cityDao.find(intCityId, cityName, country, language, countryIsoCode, region));
	}

	public CompleteList getCityNamesSuggest() {
		return new CompleteList("cityNames", cityDao.getCityNamesSuggest());
	}

	public CompleteList getCountrySuggest() {
		return new CompleteList("country", cityDao.getCountrySuggest());
	}

	public CompleteList getLanguageSuggest() {
		return new CompleteList("language", cityDao.getLanguageSuggest());
	}

	public CompleteList getCountryIsoCodeSuggest() {
		return new CompleteList("countryIsoCode", cityDao.getCountryIsoCodeSuggest());
	}

	public CompleteList getRegionSuggest() {
		return new CompleteList("region", cityDao.getRegionSuggest());
	}
}
