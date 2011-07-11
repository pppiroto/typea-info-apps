package info.typea.sample.restservice.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the COUNTRIES database table.
 * 
 */
@XmlRootElement(name="country")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Entity
@Table(name="COUNTRIES")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COUNTRY_ISO_CODE")
	private String countryIsoCode;

	private String country;

	private String region;

	//bi-directional many-to-one association to City
	@OneToMany(mappedBy="countryBean")
	private Set<City> cities;

    public Country() {
    }

	public String getCountryIsoCode() {
		return this.countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@XmlTransient
	public Set<City> getCities() {
		return this.cities;
	}
	
	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
	
}