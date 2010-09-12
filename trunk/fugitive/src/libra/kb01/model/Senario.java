package libra.kb01.model;

public class Senario {
	/** 参戦レース数 **/
	private int targetRaceCount;
	/** 的中期待レース数(ここで指定したレースに1回は的中させる) **/
	private int hitRaceRatio;
	/** 的中期待値目標 **/
	private double kitaichi;
	/** 予算(1日) **/
	private int yosanPerDay;
	/** 予算(1R) **/
	private int yosanPerRace;
	/** 期待利益(1日) **/
	private int riekiPerDay;
	/** 期待利益(1R) **/
	private int riekiPerRace;
	
	private KaimeSortArgorithm sorter;
	
	/**
	 * 買目シナリオ
	 * @param targetRaceCount 対象R数
	 * @param hitRaceRatio    対象R数に何R的中を狙うか
	 * @param yosanPerDay     1日の予算
	 * @param riekiPerDay     1日の利益目標
	 * @param sorter          ソートアルゴリズム
	 */
	public Senario(int targetRaceCount
			      ,int hitRaceRatio
			      ,int yosanPerDay
			      ,int riekiPerDay
			      ,KaimeSortArgorithm sorter
			      ){
		
		this.targetRaceCount = targetRaceCount;
		this.hitRaceRatio = hitRaceRatio;
		this.kitaichi = (double)this.hitRaceRatio / (double)this.targetRaceCount;
		this.yosanPerDay = yosanPerDay;
		this.yosanPerRace = this.yosanPerDay / this.targetRaceCount;
		this.riekiPerDay = riekiPerDay;
		this.riekiPerRace = (int)((this.riekiPerDay + this.yosanPerDay) * this.kitaichi);
		this.sorter = sorter;
	}
	public String toString() {
		return String.format(
			"<シナリオ>\n" 
		  + "対象 %dR 中 %dR、的中(期待値%.2f)\n"
		  + "%,d円の予算(1Rあたり%,d円)\n"
		  + "1日の利益目標は、%,d円(的中1Rあたり、%,d円の利益目標)"
		    ,getTargetRaceCount()
		    ,getHitRaceRatio()
		    ,getKitaichi()
		    ,getYosanPerDay()
		    ,getYosanPerRace()
		    ,getRiekiPerDay()
		    ,getRiekiPerRace()
		);
	}
	
	
	public int getTargetRaceCount() {
		return targetRaceCount;
	}
	public void setTargetRaceCount(int targetRaceCount) {
		this.targetRaceCount = targetRaceCount;
	}
	public int getHitRaceRatio() {
		return hitRaceRatio;
	}
	public void setHitRaceRatio(int hitRaceRatio) {
		this.hitRaceRatio = hitRaceRatio;
	}
	public int getYosanPerDay() {
		return yosanPerDay;
	}
	public void setYosanPerDay(int yosanPerDay) {
		this.yosanPerDay = yosanPerDay;
	}
	public int getYosanPerRace() {
		return yosanPerRace;
	}
	public void setYosanPerRace(int yosanPerRace) {
		this.yosanPerRace = yosanPerRace;
	}
	public int getRiekiPerDay() {
		return riekiPerDay;
	}
	public void setRiekiPerDay(int riekiPerDay) {
		this.riekiPerDay = riekiPerDay;
	}
	public int getRiekiPerRace() {
		return riekiPerRace;
	}
	public void setRiekiPerRace(int riekiPerRace) {
		this.riekiPerRace = riekiPerRace;
	}
	public double getKitaichi() {
		return kitaichi;
	}
	public void setKitaichi(double kitaichi) {
		this.kitaichi = kitaichi;
	}
	public KaimeSortArgorithm getSorter() {
		return sorter;
	}
	public void setSorter(KaimeSortArgorithm sorter) {
		this.sorter = sorter;
	}
}
