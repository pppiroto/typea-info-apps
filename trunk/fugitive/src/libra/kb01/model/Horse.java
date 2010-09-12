package libra.kb01.model;

/**
 * �n���
 * @author YAGI Hiroto
 */
public class Horse {
	/** �P���T���� */
	public static final double TAN_KOUJYO = 0.8;
	/** �n�A�T���� */
	public static final double REN_KOUJYO = 0.75;
	/** �n�� **/
	private int umaban;
	/** �P���I�b�Y **/
	private double tanOdds;
	/** �x����(�P��) **/
	private double shijiRitsu;
	/** �x�����΍��l **/
	private double stdev;
	
	public Horse(int umaban, double tanOdds) {
		this.umaban = umaban;
		this.tanOdds = tanOdds;
		this.shijiRitsu = TAN_KOUJYO / this.tanOdds;
	}
	@Override
	public String toString() {
		                     /* �n�� �P���I�b�Y �x���� �΍��l*/
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
