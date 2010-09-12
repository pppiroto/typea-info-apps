package libra.kb01;

import java.util.List;

import libra.kb01.model.Kaime;
import info.typea.fugitive.action.BaseActionForm;

@SuppressWarnings("serial")
public class KB01_01Form extends BaseActionForm {
	private String target_race;
	private String hit_race;
	private String yosan;
	private String rieki;
	private String[] odds   = new String[18];
	private String[] choise = new String[5];
	
	private String senarioMsg = "";
	private List<Kaime> kaime = null;
	
	public void init() {
		odds   = new String[18];
		choise = new String[5];
		senarioMsg = "";
		kaime = null;
	}
	
	public String getTarget_race() {
		return target_race;
	}
	public void setTarget_race(String target_race) {
		this.target_race = target_race;
	}
	public String getHit_race() {
		return hit_race;
	}
	public void setHit_race(String hit_race) {
		this.hit_race = hit_race;
	}
	public String getYosan() {
		return yosan;
	}
	public void setYosan(String yosan) {
		this.yosan = yosan;
	}
	public String getRieki() {
		return rieki;
	}
	public void setRieki(String rieki) {
		this.rieki = rieki;
	}
	public String[] getOdds() {
		return odds;
	}
	public void setOdds(String[] odds) {
		this.odds = odds;
	}
	public String[] getChoise() {
		return choise;
	}
	public void setChoise(String[] choise) {
		this.choise = choise;
	}
	public List<Kaime> getKaime() {
		return kaime;
	}
	public void setKaime(List<Kaime> kaime) {
		this.kaime = kaime;
	}
	public String getSenarioMsg() {
		return senarioMsg;
	}
	public void setSenarioMsg(String senarioMsg) {
		this.senarioMsg = senarioMsg;
	}
}
