package info.typea.sample.restservice.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="completelist")
public class CompleteList {
	private String name;
	private List<String> values;
	
	public CompleteList() {
	}
	public CompleteList(String name, List<String> values) {
		this.name = name;
		this.values = values;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
}
