package libra.kb01.model;

import java.util.Comparator;
import java.util.Map;

public interface KaimeSortArgorithm extends Comparator<Kaime> {
	public abstract int compare(Kaime o1, Kaime o2);
	public abstract void putChoicedHorse(int umaban, double lank);
	public abstract Map<Integer, Double> getChoised();
}