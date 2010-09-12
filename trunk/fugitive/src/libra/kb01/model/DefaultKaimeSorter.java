package libra.kb01.model;

import java.util.HashMap;
import java.util.Map;

public class DefaultKaimeSorter implements KaimeSortArgorithm {
	private Map<Integer, Double> choised = new HashMap<Integer, Double>();

	/* (non-Javadoc)
	 * @see libra.kb01.model.KaimeSortArgorithm#compare(libra.kb01.model.Kaime, libra.kb01.model.Kaime)
	 */
	public int compare(Kaime o1, Kaime o2) {
		if (o1 == null || o2 == null) return 0;
		if (o1 == null) return -1;
		if (o2 == null) return  1;
		
		
		return (int)(o2.getHoseiPoint()-o1.getHoseiPoint());  
	}
	
	
	/**
	 * @deprecated
	 * @param k
	 * @return
	 */
	private double getPoint(Kaime k) {
		double point = 0;
		Double[] ps = new Double[2]; 
		Horse h1 = k.getHourse1();
		Horse h2 = k.getHourse2();
		point += (h1.getStdev() + h2.getStdev());
		
		ps[0] = choised.get(h1.getUmaban());
		ps[1] = choised.get(h2.getUmaban());
		
		for (Double p : ps) {
			if (p != null) {
				point += p;
			}
		}
		return point;
	}
	
	/* (non-Javadoc)
	 * @see libra.kb01.model.KaimeSortArgorithm#putChoicedHorse(int, double)
	 */
	public void putChoicedHorse(int umaban, double lank) {
		choised.put(umaban, lank);
	}

	public Map<Integer, Double> getChoised() {
		return choised;
	}

}
