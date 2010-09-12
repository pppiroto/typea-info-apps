package libra.pm01.model;

import java.util.Arrays;

import info.typea.fugitive.model.ValueBean;

public class Cocomo extends ValueBean {
	private static final long serialVersionUID = 1L;
	private double kdsi;
	private double effort;
	private double tdev;
	private double fsp;
	private double prod;
	private double plan_requirements;
	private double product_design;
	private double detailed_design;
	private double code_unit_test;
	private double integration_test;
	private double total;

	private double[] phase_plan_requirements; 
	private double[] phase_product_design;      
	private double[] phase_programming;   
	private double[] phase_integration_test;
	private double[] phase_total;
    public Cocomo() {
    	phase_plan_requirements = new double[9];
    	phase_product_design    = new double[9];  
    	phase_programming       = new double[9];
    	phase_integration_test  = new double[9];
    	phase_total             = new double[9];
    	Arrays.fill(phase_plan_requirements, 0.0d);
    	Arrays.fill(phase_product_design,    0.0d);
    	Arrays.fill(phase_programming,       0.0d);
    	Arrays.fill(phase_integration_test,  0.0d);
    	Arrays.fill(phase_total,             0.0d);
    }
    public void raund() {
    	this.kdsi              = raund(this.kdsi);
    	this.effort            = raund(this.effort);
    	this.tdev              = raund(this.tdev);
    	this.fsp               = raund(this.fsp);
    	this.prod              = raund(this.prod);
    	this.plan_requirements = raund(this.plan_requirements);
    	this.product_design    = raund(this.product_design);
    	this.detailed_design   = raund(this.detailed_design);
    	this.code_unit_test    = raund(this.code_unit_test);
    	this.integration_test  = raund(this.integration_test);
    	this.total             = raund(this.total);

    	for (int i=0; i<9; i++) {
        	this.phase_plan_requirements[i] = raund(this.phase_plan_requirements[i]);
        	this.phase_product_design[i]    = raund(this.phase_product_design[i]   );  
        	this.phase_programming[i]       = raund(this.phase_programming[i]      );
        	this.phase_integration_test[i]  = raund(this.phase_integration_test[i] );
        	this.phase_total[i]             = raund(this.phase_total[i]            );
    	}
    }
    private double raund(double val) {
    	int fp = 100;
    	return Math.ceil((val * fp)) / fp;
    }
    public void calc() {
    	calc(true);
    }
	public void calc(boolean isKdsiBase) {
		
		if (isKdsiBase) {
			this.effort = 2.4d * Math.exp(1.05d * Math.log(this.kdsi));
		} else {
			this.kdsi = Math.exp(Math.log(this.effort / 2.4d) / 1.05d);
		}
		this.tdev   = 2.5d * Math.exp(0.38d * Math.log(this.effort));
		this.fsp    = this.effort / this.tdev;
		this.prod   = this.kdsi / this.effort;
		this.plan_requirements = this.effort * (6d / 100d);
		this.product_design    = this.effort * (16d / 100d);
		this.detailed_design   = this.effort * ((-0.7213d * Math.log(this.kdsi) + 26.5d) / 100d);
		this.code_unit_test    = this.effort * ((-1.4427  * Math.log(this.kdsi) + 43d) / 100d);
		this.integration_test  = this.effort - (this.product_design + this.detailed_design + this.code_unit_test);
		
		this.total = this.plan_requirements
		           + this.product_design
		           + this.detailed_design
		           + this.code_unit_test
		           + this.integration_test
		           ;
		
		phase_plan_requirements[0] = this.plan_requirements * (46d / 100d);
		phase_plan_requirements[1] = this.plan_requirements * (20d / 100d);
		phase_plan_requirements[2] = this.plan_requirements * ( 3d / 100d);
		phase_plan_requirements[3] = this.plan_requirements * ( 3d / 100d);
		phase_plan_requirements[4] = this.plan_requirements * ( 6d / 100d);
		phase_plan_requirements[5] = this.plan_requirements * (15d / 100d);
		phase_plan_requirements[6] = this.plan_requirements * ( 2d / 100d);
		phase_plan_requirements[7] = this.plan_requirements * ( 5d / 100d);
		for(int i=0; i<8; i++) {
			phase_plan_requirements[8] += phase_plan_requirements[i];
		}
		
		phase_product_design[0] = this.product_design * (15d / 100d);
		phase_product_design[1] = this.product_design * (40d / 100d);
		phase_product_design[2] = this.product_design * (14d / 100d);
		phase_product_design[3] = this.product_design * ( 5d / 100d);
		phase_product_design[4] = this.product_design * ( 6d / 100d);
		phase_product_design[5] = this.product_design * (11d / 100d);
		phase_product_design[6] = this.product_design * ( 2d / 100d);
		phase_product_design[7] = this.product_design * ( 7d / 100d);
		for(int i=0; i<8; i++) {
			phase_product_design[8] += phase_product_design[i];
		}
		
		phase_programming[0] = (this.detailed_design + this.code_unit_test) * ( 5d / 100d);
		phase_programming[1] = (this.detailed_design + this.code_unit_test) * (10d / 100d);
		phase_programming[2] = (this.detailed_design + this.code_unit_test) * (58d / 100d);
		phase_programming[3] = (this.detailed_design + this.code_unit_test) * ( 4d / 100d);
		phase_programming[4] = (this.detailed_design + this.code_unit_test) * ( 6d / 100d);
		phase_programming[5] = (this.detailed_design + this.code_unit_test) * ( 6d / 100d);
		phase_programming[6] = (this.detailed_design + this.code_unit_test) * ( 6d / 100d);
		phase_programming[7] = (this.detailed_design + this.code_unit_test) * ( 5d / 100d);
		for(int i=0; i<8; i++) {
			phase_programming[8] += phase_programming[i];
		}
		
		phase_integration_test[0] = (this.integration_test) * ( 3d / 100d);
		phase_integration_test[1] = (this.integration_test) * ( 6d / 100d);
		phase_integration_test[2] = (this.integration_test) * (34d / 100d);
		phase_integration_test[3] = (this.integration_test) * ( 2d / 100d);
		phase_integration_test[4] = (this.integration_test) * (34d / 100d);
		phase_integration_test[5] = (this.integration_test) * ( 7d / 100d);
		phase_integration_test[6] = (this.integration_test) * ( 7d / 100d);
		phase_integration_test[7] = (this.integration_test) * ( 7d / 100d);
		for(int i=0; i<8; i++) {
			phase_integration_test[8] += phase_integration_test[i];
		}
		
		for(int i=0; i<=8; i++) {
			phase_total[i] = phase_plan_requirements[i] 
  				    	   + phase_product_design[i]      
					       + phase_programming[i]       
					       + phase_integration_test[i]  
					       + phase_total[i]
					       ;
		}
	}
	/**
	 * @return the code_unit_test
	 */
	public double getCode_unit_test() {
		return code_unit_test;
	}
	/**
	 * @param code_unit_test the code_unit_test to set
	 */
	public void setCode_unit_test(double code_unit_test) {
		this.code_unit_test = code_unit_test;
	}
	/**
	 * @return the detailed_design
	 */
	public double getDetailed_design() {
		return detailed_design;
	}
	/**
	 * @param detailed_design the detailed_design to set
	 */
	public void setDetailed_design(double detailed_design) {
		this.detailed_design = detailed_design;
	}
	/**
	 * @return the effort
	 */
	public double getEffort() {
		return effort;
	}
	/**
	 * @param effort the effort to set
	 */
	public void setEffort(double effort) {
		this.effort = effort;
	}
	/**
	 * @return the fsp
	 */
	public double getFsp() {
		return fsp;
	}
	/**
	 * @param fsp the fsp to set
	 */
	public void setFsp(double fsp) {
		this.fsp = fsp;
	}
	/**
	 * @return the integration_test
	 */
	public double getIntegration_test() {
		return integration_test;
	}
	/**
	 * @param integration_test the integration_test to set
	 */
	public void setIntegration_test(double integration_test) {
		this.integration_test = integration_test;
	}
	/**
	 * @return the kdsi
	 */
	public double getKdsi() {
		return kdsi;
	}
	/**
	 * @param kdsi the kdsi to set
	 */
	public void setKdsi(double kdsi) {
		this.kdsi = kdsi;
	}
	/**
	 * @return the phase_integration_test
	 */
	public double[] getPhase_integration_test() {
		return phase_integration_test;
	}
	/**
	 * @param phase_integration_test the phase_integration_test to set
	 */
	public void setPhase_integration_test(double[] phase_integration_test) {
		this.phase_integration_test = phase_integration_test;
	}
	/**
	 * @return the phase_plan_requirements
	 */
	public double[] getPhase_plan_requirements() {
		return phase_plan_requirements;
	}
	/**
	 * @param phase_plan_requirements the phase_plan_requirements to set
	 */
	public void setPhase_plan_requirements(double[] phase_plan_requirements) {
		this.phase_plan_requirements = phase_plan_requirements;
	}
	/**
	 * @return the phase_product_design
	 */
	public double[] getPhase_product_design() {
		return phase_product_design;
	}
	/**
	 * @param phase_product_design the phase_product_design to set
	 */
	public void setPhase_product_design(double[] phase_product_design) {
		this.phase_product_design = phase_product_design;
	}
	/**
	 * @return the phase_programming
	 */
	public double[] getPhase_programming() {
		return phase_programming;
	}
	/**
	 * @param phase_programming the phase_programming to set
	 */
	public void setPhase_programming(double[] phase_programming) {
		this.phase_programming = phase_programming;
	}
	/**
	 * @return the phase_total
	 */
	public double[] getPhase_total() {
		return phase_total;
	}
	/**
	 * @param phase_total the phase_total to set
	 */
	public void setPhase_total(double[] phase_total) {
		this.phase_total = phase_total;
	}
	/**
	 * @return the plan_requirements
	 */
	public double getPlan_requirements() {
		return plan_requirements;
	}
	/**
	 * @param plan_requirements the plan_requirements to set
	 */
	public void setPlan_requirements(double plan_requirements) {
		this.plan_requirements = plan_requirements;
	}
	/**
	 * @return the prod
	 */
	public double getProd() {
		return prod;
	}
	/**
	 * @param prod the prod to set
	 */
	public void setProd(double prod) {
		this.prod = prod;
	}
	/**
	 * @return the product_design
	 */
	public double getProduct_design() {
		return product_design;
	}
	/**
	 * @param product_design the product_design to set
	 */
	public void setProduct_design(double product_design) {
		this.product_design = product_design;
	}
	/**
	 * @return the tdev
	 */
	public double getTdev() {
		return tdev;
	}
	/**
	 * @param tdev the tdev to set
	 */
	public void setTdev(double tdev) {
		this.tdev = tdev;
	}
	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}	
	
}
