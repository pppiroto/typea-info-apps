package info.typea.sample.restservice.rs;

import info.typea.sample.restservice.dto.CompleteList;
import info.typea.sample.restservice.entity.Cities;
import info.typea.sample.restservice.entity.City;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/city")
public interface CityResource {

	@GET
	@Path("/{cityId}")
	@Produces("{application/xml}")
	public City getCity(@PathParam("cityId") String cityId);
	
	@GET
	@Path("/all")
	@Produces("{application/xml}")
	public Cities getCities();

	@GET
	@Path("/search")
	@Produces("{application/xml}")
	public Cities find(
			@QueryParam("cityId")  			String cityId,
			@QueryParam("cityName") 		String cityName,
			@QueryParam("country")  		String country,
			@QueryParam("language") 		String language,
			@QueryParam("countryIsoCode") 	String countryIsoCode,
			@QueryParam("region")         	String region);
	
	@GET
	@Path("/suggest/cityNames")
	@Produces("{application/xml}")
	public CompleteList getCityNamesSuggest();
	
	@GET
	@Path("/suggest/country")
	@Produces("{application/xml}")
	public CompleteList getCountrySuggest();
	
	@GET
	@Path("/suggest/language")
	@Produces("{application/xml}")
	public CompleteList getLanguageSuggest();

	@GET
	@Path("/suggest/countryIsoCode")
	@Produces("{application/xml}")
	public CompleteList getCountryIsoCodeSuggest();
	
	@GET
	@Path("/suggest/region")
	@Produces("{application/xml}")
	public CompleteList getRegionSuggest();
}
