package libra.zz01.model;

import info.typea.fugitive.model.CsvValueBean;

public class Employee extends CsvValueBean {
	private static final long serialVersionUID = 1L;
	
	private String employee_id;
	private String first_name;
	private String last_name;
	private String email;
	private String phone_number;
	private String hire_date;
	private String job_id;
	private String salary;
	private String commission_pct;
	private String manager_id;
	private String department_id ;

	@Override
	public Object[] getFieldData() {
		return new Object[]{
				employee_id,
				first_name,
				last_name,
				email,
				phone_number,
				hire_date,
				job_id,
				salary,
				commission_pct,
				manager_id,
				department_id
		};
	}
	
	public String getCommission_pct() {
		return commission_pct;
	}
	public void setCommission_pct(String commission_pct) {
		this.commission_pct = commission_pct;
	}
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getHire_date() {
		return hire_date;
	}
	public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
}