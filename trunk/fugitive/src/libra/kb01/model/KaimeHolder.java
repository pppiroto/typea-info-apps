package libra.kb01.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 買目を保持
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
	 * 馬連における指定の軸馬を持つ買い目に対する支持率、予想オッズの計算処理
	 * @param horse 出走馬
	 */
	public void initialize(List<Horse> horses) {
		
		// 買目の支持率、予想オッズ算出(按分)
		for (int i=0; i<kaimelist.size(); i++) {
			Kaime k2 = kaimelist.get(i);
			
			// 軸馬以外の支持率合計
			double ttlSijiritsu = 0.0;
			for (Horse h : horses) {
				if (!k2.getHourse1().equals(h)) {
					ttlSijiritsu += h.getShijiRitsu();
				}
			}
			// 馬連の支持率を算出(軸馬以外の支持率を按分し、軸馬の支持率と掛け、× ２(順不同)する)
			k2.setSijiRitu(
				k2.getHourse1().getShijiRitsu()  	
				 * (k2.getHourse2().getShijiRitsu() / ttlSijiritsu)
				 * 2
			);
			// 支持率から予想オッズを算出
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
