package info.typea.sample.restservice.entity;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * The persistent class for the CITIES database table.
 * 
 * @see http://docs.huihoo.com/apache/cxf/2.2.4/jax-rs.html#JAX-RS-MessageBodyProviders
 */
@XmlRootElement(name="city")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Entity
@Table(name="CITIES")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CITY_ID")
	private Integer cityId;

	private String airport;

	@Column(name="CITY_NAME")
	private String cityName;

	private String country;

	private String language;

	//bi-directional many-to-one association to Country
    @ManyToOne
	@JoinColumn(name="COUNTRY_ISO_CODE")
	private Country countryBean;

    public City() {
    }

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getAirport() {
		return this.airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Country getCountryBean() {
		return this.countryBean;
	}
	
	public void setCountryBean(Country countryBean) {
		this.countryBean = countryBean;
	}
	
}