package libra.kb01.model;

public class Senario {
	/** �Q�탌�[�X�� **/
	private int targetRaceCount;
	/** �I�����҃��[�X��(�����Ŏw�肵�����[�X��1��͓I��������) **/
	private int hitRaceRatio;
	/** �I�����Ғl�ڕW **/
	private double kitaichi;
	/** �\�Z(1��) **/
	private int yosanPerDay;
	/** �\�Z(1R) **/
	private int yosanPerRace;
	/** ���җ��v(1��) **/
	private int riekiPerDay;
	/** ���җ��v(1R) **/
	private int riekiPerRace;
	
	private KaimeSortArgorithm sorter;
	
	/**
	 * ���ڃV�i���I
	 * @param targetRaceCount �Ώ�R��
	 * @param hitRaceRatio    �Ώ�R���ɉ�R�I����_����
	 * @param yosanPerDay     1���̗\�Z
	 * @param riekiPerDay     1���̗��v�ڕW
	 * @param sorter          �\�[�g�A���S���Y��
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
			"<�V�i���I>\n" 
		  + "�Ώ� %dR �� %dR�A�I��(���Ғl%.2f)\n"
		  + "%,d�~�̗\�Z(1R������%,d�~)\n"
		  + "1���̗��v�ڕW�́A%,d�~(�I��1R������A%,d�~�̗��v�ڕW)"
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
