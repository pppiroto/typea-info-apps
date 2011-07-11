package info.typea.sample.restservice.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cities")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Cities {
	private List<City> cities;
	
	public Cities() {
	}
	
	public Cities(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCity() {
		return cities;
	}

	public void setCity(List<City> cities) {
		this.cities = cities;
	}
}
