package libra.kb01.model;
/**
 * ����(�n�A�j �̊e����ێ�
 * @author YAGI Hiroto
 */
public class Kaime implements Comparable<Kaime> {
	/** ���� **/
	public static final String MARK_KAI     = "��";
	/** ���� **/
	public static final String MARK_CHUI    = "��";
	/** ���ڔn **/
	public static final String MARK_CHUMOKU      = "��";
	/** ������ **/
	public static final String MARK_MIOKURI = "�Q";
	
	/** 1���� **/
	private Horse horse1;
	/** 2���� **/
	private Horse horse2;
	/** �g�ݍ��킹�x���� **/
	private double sijiRitu;
	/** �\�z�I�b�Y **/
	private double yosouOdds;
	/** ���̔��ڂɑ΂��铊�����z **/
	private int tousiKin;
	/** ���̔��ڂɑ΂�����Ҕz���z **/
	private int kitaiHaitou;
	/** KaimeSortArgorithm�ɂ���ă\�[�g���ꂽ���ɁA���̔��ڏo���܂ł́A�x�����J�o���b�W **/
	private double coverage;

	private double basePoint;
	
	private double hoseiPoint;

	private int yosanPerPrace;
	
	
	/** ���}�[�N **/
	private String coverageMark = "";
	
	private int yosanHi;
	
	public Kaime(Horse hourse1, Horse hourse2) {
		this.horse1 = hourse1;
		this.horse2 = hourse2;
	}
	public int compareTo(Kaime o) {
		if (o == null) return -1;
		return this.toKeyString().compareTo(o.toKeyString());
	}
	public String toKeyString() {
		return String.format(
				"%02d%02d"
			, this.getHourse1().getUmaban()
			, this.getHourse2().getUmaban()
			);
	}
	public String getUmaren() {
		return String.format("%2d - %2d", horse1.getUmaban() , horse2.getUmaban());
	}
	public String getCoverageStr() {
		return String.format("%4.2f%%", this.getCoverage());
	}
	public String getYosouOddsStr() {
		return String.format("%8.2f", this.yosouOdds);
	}
	public String getTousiKinStr() {
		return String.format("%,8d�~", getTousiKin());
	}	
	public String getKitaiHaitouStr() {
		return String.format("%,12d�~", getKitaiHaitou());
	}
	public String getYosanHiStr() {
		return String.format("(%,8d)", getYosanHi());
	}	
	public String getHoseiPointStr() {
		return String.format("%4.0f", getHoseiPoint());
	}
	public String getYosanTaihiStr() {
		return String.format("%+d�~", getYosanTaihi());
	}
	public String toString() {
		boolean flag = true;
		String ret = null;
		ret = String.format(
				/*   �J�o���b�W   �Pt. �␳Pt. �n�A        �x����        �\�z�I�b�Y   �����z    ���Ҕz��        �\�Z��*/
				"%s %4.2f%% %4.0f %4.0f %2d - %2d  %8.4f%%  %8.2f �~ %,8d�~  = %,12d�~   (%,8d)"
				, this.getCoverageMark()
				, this.getCoverage()
				, this.getBasePoint()
				, this.getHoseiPoint()
				, horse1.getUmaban()
				, horse2.getUmaban()
				, this.sijiRitu * 100
				, this.yosouOdds
				, this.getTousiKin()
				, this.getKitaiHaitou()
				, this.getYosanHi()
				);
		if (flag) {
			ret += String.format(
					/* �P���I�b�Y �x���� �΍��l*/
					"  %8.2f  %8.2f%% %3.0f %8.2f  %8.2f%% %3.0f"
					, horse1.getTanOdds()
					, horse1.getShijiRitsu() * 100
					, horse1.getStdev()
					, horse2.getTanOdds()
					, horse2.getShijiRitsu() * 100
					, horse2.getStdev()
					);
		}
		return ret;
	}
	public int getYosanTaihi() {
		return kitaiHaitou - this.yosanHi ;
	}
	public Horse getHourse1() {
		return horse1;
	}
	public void setHourse1(Horse hourse1) {
		this.horse1 = hourse1;
	}
	public Horse getHourse2() {
		return horse2;
	}
	public void setHourse2(Horse hourse2) {
		this.horse2 = hourse2;
	}
	public double getSijiRitu() {
		return sijiRitu;
	}
	public void setSijiRitu(double sijiRitu) {
		this.sijiRitu = sijiRitu;
	}
	public double getYosouOdds() {
		return yosouOdds;
	}
	public void setYosouOdds(double yosouOdds) {
		this.yosouOdds = yosouOdds;
	}
	public int getTousiKin() {
		return tousiKin;
	}
	public void setTousiKin(int tousiKin) {
		this.tousiKin = tousiKin;
	}
	public int getKitaiHaitou() {
		return kitaiHaitou;
	}
	public void setKitaiHaitou(int kitaiHaitou) {
		this.kitaiHaitou = kitaiHaitou;
	}
	public double getCoverage() {
		return coverage;
	}
	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}
	public String getCoverageMark() {
		return coverageMark;
	}
	public void setCoverageMark(String coverageMark) {
		this.coverageMark = coverageMark;
	}
	public int getYosanHi() {
		return yosanHi;
	}
	public void setYosanHi(int yosanHi) {
		this.yosanHi = yosanHi;
	}
	public double getHoseiPoint() {
		return hoseiPoint;
	}
	public void setHoseiPoint(double hoseiPoint) {
		this.hoseiPoint = hoseiPoint;
	}
	public double getBasePoint() {
		return basePoint;
	}
	public void setBasePoint(double basePoint) {
		this.basePoint = basePoint;
	}
	public int getYosanPerPrace() {
		return yosanPerPrace;
	}
	public void setYosanPerPrace(int yosanPerPrace) {
		this.yosanPerPrace = yosanPerPrace;
	}
	
}
