package libra.kb01.model;

/**
 * 馬情報
 * @author YAGI Hiroto
 */
public class Horse {
	/** 単勝控除率 */
	public static final double TAN_KOUJYO = 0.8;
	/** 馬連控除率 */
	public static final double REN_KOUJYO = 0.75;
	/** 馬番 **/
	private int umaban;
	/** 単勝オッズ **/
	private double tanOdds;
	/** 支持率(単勝) **/
	private double shijiRitsu;
	/** 支持率偏差値 **/
	private double stdev;
	
	public Horse(int umaban, double tanOdds) {
		this.umaban = umaban;
		this.tanOdds = tanOdds;
		this.shijiRitsu = TAN_KOUJYO / this.tanOdds;
	}
	@Override
	public String toString() {
		                     /* 馬番 単勝オッズ 支持率 偏差値*/
		return String.format("%4d  %8.1f  %8.3f %3.0f", this.umaban, this.tanOdds, this.shijiRitsu, this.stdev);
	}
	public boolean equals(Horse horse) {
		if (horse == null) return false;
		return this.getUmaban() == horse.getUmaban();
	}
	@Override
	public int hashCode() {
		return this.getUmaban();
	}
	public int getUmaban() {
		return umaban;
	}
	public void setUmaban(int umaban) {
		this.umaban = umaban;
	}
	public double getTanOdds() {
		return tanOdds;
	}
	public void setTanOdds(double tanOdds) {
		this.tanOdds = tanOdds;
	}
	public double getShijiRitsu() {
		return shijiRitsu;
	}
	public void setShijiRitsu(double shijiRitsu) {
		this.shijiRitsu = shijiRitsu;
	}
	public double getStdev() {
		return stdev;
	}
	public void setStdev(double stdev) {
		this.stdev = stdev;
	}
	public double getPoint() {
		return this.shijiRitsu * 100.0;
	}
}
