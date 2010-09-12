package libra.kb01.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Syutsuba {
	private List<Horse> horses = new ArrayList<Horse>();
	private KaimeHolder kaimeHolder  = new KaimeHolder();
	private double totalSijiritu;
	private double totalPoint;
	private double pointStdev;
	
	public void addHouse(Horse horse) {
		horses.add(horse);
		// System.out.println(horse);
		
		totalSijiritu += horse.getShijiRitsu();
		totalPoint    += horse.getPoint();
	}
	public Horse getHorseByUmaban(int umaban) {
		Horse h = horses.get(umaban - 1);
		if (h.getUmaban() == umaban) {
			return h;
		}
		for (Horse h2 : horses) {
			if (h2.getUmaban() == umaban) {
				return h2;
			}
		}
		return null;
	}
	public void initialize() {
		// 単勝ポイント(支持率*100)の標準偏差を計算
		double   datacnt   = (double)horses.size();
		double   pointAvg  = totalPoint / datacnt;
		BigDecimal bunsan  = new BigDecimal(0.0);
		for (int i=0; i<horses.size(); i++) {
			bunsan = bunsan.add(
						BigDecimal.valueOf(
								Math.pow(horses.get(i).getPoint() - pointAvg, 2)
						)
					);
		}
		bunsan = BigDecimal.valueOf(bunsan.doubleValue() / datacnt); 
		double stdev = Math.sqrt(bunsan.doubleValue());
		for (int i=0; i<horses.size(); i++) {
			Horse h = horses.get(i);
			h.setStdev(
					((h.getPoint() - pointAvg) * 10.0 / stdev) + 50
				);
			//System.out.println(h);
		}
		
		for (int i=0; i<horses.size()-1; i++) {
			for (int j=i+1; j<horses.size(); j++) {
				kaimeHolder.add(new Kaime(horses.get(i), horses.get(j)));
			}
		}
		for (int i=0; i<horses.size()-1; i++) {
			kaimeHolder.initialize(horses);
		}
		// System.out.println(kaimeHolder);
	}
	
	private double getPoint(Kaime k, Map<Integer, Double> choised, double stdev) {
		
		//System.out.println(stdev);
		
		double point = k.getBasePoint();
		
		Double[] ps = new Double[2]; 
		ps[0] = choised.get(k.getHourse1().getUmaban());
		ps[1] = choised.get(k.getHourse2().getUmaban());
		
		for (int i=0; i<ps.length; i++) {
			if (ps[i] != null) {
				point += (ps[i] * (1 + Math.abs(2 - stdev)));
			}
		}
		
		return point;
	}
	public List<Kaime> calc(KaimeSortArgorithm sorter) {
		Map<Integer, Double> choised = sorter.getChoised();
		List<Kaime> list = kaimeHolder.getKaimelist();
		
		double lstTtl = 0.0;
		for (Kaime k : list) {
			lstTtl += (k.getSijiRitu() * 100.0);
		}
		
		// 買目ポイント(支持率*100)の標準偏差を計算
		double   datacnt   = (double)list.size();
		double   pointAvg  = lstTtl / datacnt;
		BigDecimal bunsan  = new BigDecimal(0.0);
		for (int i=0; i<list.size(); i++) {
			bunsan = bunsan.add(
						BigDecimal.valueOf(
								Math.pow(list.get(i).getSijiRitu() * 100.0 - pointAvg, 2)
						)
					);
		}
		bunsan = BigDecimal.valueOf(bunsan.doubleValue() / datacnt); 
		
		double stdev = Math.sqrt(bunsan.doubleValue());
		for (Kaime k : list ) {
			k.setBasePoint(
					(((k.getSijiRitu() * 100.0) - pointAvg) * 10.0 / stdev) + 50.0
				);
		}
		setPointStdev(stdev);
		
		for (Kaime k : list) {
			k.setHoseiPoint(getPoint(k, choised, stdev));
		}
		
		kaimeHolder.sort(sorter);
		// System.out.println(kaimeHolder);
		return kaimeHolder.getKaimelist(); 
	}
	public double getMaxSijiritsu() {
		return kaimeHolder.getMaxSijiritsu();
	}
	public double getPointStdev() {
		return pointStdev;
	}
	public void setPointStdev(double pointStdev) {
		this.pointStdev = pointStdev;
	}
}
