package libra.kb01.model.test;

import java.util.List;

import libra.kb01.model.DefaultKaimeSorter;
import libra.kb01.model.Kaime;
import libra.kb01.model.Senario;
import libra.kb01.model.UmarenEngine;

public class UmarenEnginTest {
	public static void main(String[] args) {
		UmarenEnginTest test = new UmarenEnginTest();
		test.doUmarenTest();
	}
	public void doUmarenTest() {
		int[] choised = new int[]{5};
		
		Senario senario = new Senario(10, 4, 15000, 10000, new DefaultKaimeSorter());
		System.out.println(senario);

		UmarenEngine engine = new UmarenEngine(senario, choised);
		engine.setSyutsubaByOdds(getOdds(1));
		engine.initialize();
		
		List<Kaime> l = engine.calc();
		
		for (Kaime k : l) {
			System.out.println(k);
		}
		
	}
	public double[] getOdds(int id) {
		switch(id) {
		case 1:
			// H20 2k chukyo 5d 1R
			return new double[] { 6.0   ,4.1  ,4.1   ,135.1 ,17.6
					             ,23.7  ,93.7 ,6.7   ,113.9 ,72.7
					             ,282.5 ,13.5 ,142.1 ,233.3 ,64.9
					             ,4.7
					            };
		case 2:
			// H20 2k chukyo 5d 2R
			return new double[] { 123.7 ,7.7  ,3.2   ,76.1  ,12.5
					             ,121.6 ,86.6 ,15.5  ,131.2 ,4.9 
					             ,29.6  ,84.0 ,15.4  ,6.4   ,47.1
					             ,221.6 ,14.9 ,16.8
					            };
		default:
			
		}
		return null;
	}
}
