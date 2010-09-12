package libra.kb01;

import info.typea.fugitive.logic.ApplicationLogic;

import java.util.List;

import libra.kb01.model.Kaime;
import libra.kb01.model.Senario;
import libra.kb01.model.UmarenEngine;

public class KB01Logic extends ApplicationLogic {
	public List<Kaime> doCalc(Senario senario, double[] odds, int[] choised) {
		
		// System.out.println(senario);

		UmarenEngine engine = new UmarenEngine(senario, choised);
		engine.setSyutsubaByOdds(odds);
		engine.initialize();
		List<Kaime> list = engine.calc();
		
		//for (Kaime k : list) {
		//	System.out.println(k);
		//}
		
		return list;
	}
}
