package libra.kb01.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UmarenEngine {
	private Syutsuba syutsuba = new Syutsuba();
	private Senario senario;
	private int[] choised;
	private final double BASE_STDEV   = 18.0;
	private final double DEGREE_RATIO = 0.85;
	public UmarenEngine(Senario senario, int[] choised) {
		this.senario = senario;
		this.choised = choised;
	}
	
	public void setSyutsubaByOdds(double[] odds) {
		for(int i=0; i<odds.length; i++) {
			syutsuba.addHouse(new Horse(i + 1, odds[i]));
		}
	}
	public void initialize() {
		syutsuba.initialize();
	}
	

	
	public List<Kaime> calc() {
		
		KaimeSortArgorithm sorter = senario.getSorter();
		
		double lank = BASE_STDEV; // â∫ë ÇóöÇ©ÇπÇÈïŒç∑íl
		for (int i=0; i<choised.length; i++) {
			sorter.putChoicedHorse(choised[i], lank);
			lank *= DEGREE_RATIO;
		}

		List<Kaime> sorted = syutsuba.calc(sorter);
		double yosanPerRace = (double)senario.getYosanPerRace();
		double yosan = yosanPerRace;
		double rieki = (double)senario.getRiekiPerRace();
		double cover = 0.0;
		double kitaich = senario.getKitaichi();
		int    totalBet = 0;
		List<Kaime> result = new ArrayList<Kaime>();
		boolean kitaitOverFlag = false;
		for (Kaime k : sorted) {
			double tmpbet = rieki / k.getYosouOdds();
			int bet = (int)((tmpbet + 99.0) / 100.0);
			bet *= 100;
			if (bet < 100) {
				bet = 100;
			}
			yosan -=bet;
			totalBet += bet;
			cover += k.getSijiRitu();
			k.setTousiKin(bet);
			k.setKitaiHaitou((int)(k.getTousiKin() * k.getYosouOdds()));
			k.setCoverage(cover);
			k.setYosanHi(totalBet);
			k.setYosanPerPrace((int)yosanPerRace);
			
			if (!kitaitOverFlag) {
				if (((int)yosan) > 0) {
					k.setCoverageMark(Kaime.MARK_KAI);
				} else {
					k.setCoverageMark(Kaime.MARK_CHUI);
				}
			} else {
				k.setCoverageMark(Kaime.MARK_MIOKURI);
			}
			
			int[] umaban = {k.getHourse1().getUmaban()
					       , k.getHourse2().getUmaban()};
							
			for (int ub : umaban) {
				for (int c : choised) {
					if (ub == c) {
						String newM = k.getCoverageMark();
						if (newM.equals(Kaime.MARK_MIOKURI)) {
							newM = Kaime.MARK_CHUMOKU;
						} else {
							newM += Kaime.MARK_CHUMOKU;
						}
						k.setCoverageMark(newM);
					}
				}
			}
			
			if (cover > kitaich) {
				kitaitOverFlag = true;
			}
			result.add(k);
			//System.out.println(k);
		}
		return result;
	}
}
