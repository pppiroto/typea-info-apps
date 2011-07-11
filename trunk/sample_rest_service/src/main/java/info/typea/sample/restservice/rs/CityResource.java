package info.typea.sample.restservice.rs;

import info.typea.sample.restservice.dto.CompleteList;
import info.typea.sample.restservice.entity.Cities;
import info.typea.sample.restservice.entity.City;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/city")
public interface CityResource {

	@POST
	public City insertCity(City city);
	
	@GET
	@Path("/{cityId}")
	public City getCity(@PathParam("cityId") String cityId);
	
	@GET
	@Path("/all")
	public Cities getCities();

	@GET
	@Path("/search")
	public Cities find(
			@QueryParam("cityId")  			String cityId,
			@QueryParam("cityName") 		String cityName,
			@QueryParam("country")  		String country,
			@QueryParam("language") 		String language,
			@QueryParam("countryIsoCode") 	String countryIsoCode,
			@QueryParam("region")         	String region);
	
	@GET
	@Path("/suggest/cityNames")
	public CompleteList getCityNamesSuggest();
	
	@GET
	@Path("/suggest/country")
	public CompleteList getCountrySuggest();
	
	@GET
	@Path("/suggest/language")
	public CompleteList getLanguageSuggest();

	@GET
	@Path("/suggest/countryIsoCode")
	public CompleteList getCountryIsoCodeSuggest();
	
	@GET
	@Path("/suggest/region")
	public CompleteList getRegionSuggest();
}
