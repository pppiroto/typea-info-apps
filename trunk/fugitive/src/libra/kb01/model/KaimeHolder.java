package libra.kb01.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ���ڂ�ێ�
 * @author YAGI Hiroto
 */
public class KaimeHolder {
	private List<Kaime> kaimelist = new ArrayList<Kaime>();
	
	public void add(Kaime kaime) {
		kaimelist.add(kaime);
	}
	public void sort(KaimeSortArgorithm sorter) {
		Collections.sort(kaimelist, sorter);
	}
	/**
	 * �n�A�ɂ�����w��̎��n���������ڂɑ΂���x�����A�\�z�I�b�Y�̌v�Z����
	 * @param horse �o���n
	 */
	public void initialize(List<Horse> horses) {
		
		// ���ڂ̎x�����A�\�z�I�b�Y�Z�o(��)
		for (int i=0; i<kaimelist.size(); i++) {
			Kaime k2 = kaimelist.get(i);
			
			// ���n�ȊO�̎x�������v
			double ttlSijiritsu = 0.0;
			for (Horse h : horses) {
				if (!k2.getHourse1().equals(h)) {
					ttlSijiritsu += h.getShijiRitsu();
				}
			}
			// �n�A�̎x�������Z�o(���n�ȊO�̎x�����������A���n�̎x�����Ɗ|���A�~ �Q(���s��)����)
			k2.setSijiRitu(
				k2.getHourse1().getShijiRitsu()  	
				 * (k2.getHourse2().getShijiRitsu() / ttlSijiritsu)
				 * 2
			);
			// �x��������\�z�I�b�Y���Z�o
			k2.setYosouOdds(
				Horse.REN_KOUJYO / k2.getSijiRitu()
			);
		}
	}
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for (Kaime k : kaimelist) {
			buf.append(k + "\r\n");
		}
		return buf.toString();
	}

	public double getMaxSijiritsu() {
		double sijiritsu = 0.0;
		for (Kaime k : kaimelist) {
			if (k.getSijiRitu() > sijiritsu) {
				sijiritsu = k.getSijiRitu();
			}
		}
		return sijiritsu;
	}
	public List<Kaime> getKaimelist() {
		return kaimelist;
	}
	
}
