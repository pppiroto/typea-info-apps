package libra.zz01;

import info.typea.fugitive.action.BaseActionForm;

/**
 * Employee データ一覧検索用Formクラス(サンプルプログラム)
 * 
 * @author totec yagi
 */
public class ZZ01_01Form extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private String first_name;
    private String last_name;
    private String email;
    private String salary_from;
    private String salary_to;
    
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the first_name.
     */
    public String getFirst_name() {
        return first_name;
    }
    /**
     * @param first_name The first_name to set.
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    /**
     * @return Returns the last_name.
     */
    public String getLast_name() {
        return last_name;
    }
    /**
     * @param last_name The last_name to set.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    /**
     * @return Returns the salary_from.
     */
    public String getSalary_from() {
        return salary_from;
    }
    /**
     * @param salary_from The salary_from to set.
     */
    public void setSalary_from(String salary_from) {
        this.salary_from = salary_from;
    }
    /**
     * @return Returns the salary_to.
     */
    public String getSalary_to() {
        return salary_to;
    }
    /**
     * @param salary_to The salary_to to set.
     */
    public void setSalary_to(String salary_to) {
        this.salary_to = salary_to;
    }
}
