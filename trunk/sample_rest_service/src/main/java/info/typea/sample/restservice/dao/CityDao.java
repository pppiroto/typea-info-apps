package info.typea.sample.restservice.dao;

import info.typea.sample.restservice.entity.City;

import java.util.List;

public interface CityDao {
	public City findById(String cityId);
	public City insertCity(City city);
	public City updateCity(City city);
	public City deleteById(String cityId);
	
	public List<City> findAll();
	public List<City> find(Integer cityId, String cityName, String country,
						   String language, String countryIsoCode, String region);
	
	public List<String> getCityNamesSuggest();
	public List<String> getCountrySuggest();
	public List<String> getLanguageSuggest();
	public List<String> getCountryIsoCodeSuggest();
	public List<String> getRegionSuggest();
}
