package libra.zz01;

import info.typea.fugitive.database.DefaultDao;
import info.typea.fugitive.database.Parameter;
import info.typea.fugitive.util.StringUtil;

import java.sql.Connection;
import java.util.List;

import libra.zz01.model.Department;
import libra.zz01.model.Employee;


/**
 * 業務アプリケーション用データアクセスクラス(サンプルプログラム)
 * 
 * @author totec yagi
 */
public class ZZ01Dao extends DefaultDao {
	public ZZ01Dao(){
		super("jdbc.datasource.hr");
	}
	/**
	 * Employeeの一覧を検索する
	 * @param param
	 * @return Ｅｍｐｌｏｙｅｅのリスト
	 */
	public List findEmployees(Parameter param) {
		String salaryFrom = param.get(4).toString();
		String salaryTo   = param.get(5).toString();
		String sql = null;

		// salary 条件の有無により、使用するSQLを変更
		Object[] params = null;
		if (StringUtil.isBlank(salaryFrom) && StringUtil.isBlank(salaryTo)) {
			sql = "zz01.01";
			params = param.toObjectArray(3);
		} else {
			sql = "zz01.02";
			params = param.toObjectArray();
		}
		
		// 指定条件で、Employeeのリストを取得する
		return getBeanList(Employee.class, getSql(sql), params);
	}
	
	/**
	 * Employeeを検索する
	 * @param employeeId
	 * @return Employee
	 */
	public Employee findEmployee(String employeeId) {
		return (Employee) getBean(Employee.class, getSql("zz01.03"), 
							Parameter.makeObjectArray(employeeId));
	}
	/**
	 * Departmentの一覧を検索する
	 * @return Departmentのリスト
	 */
	public List findDepartments() {
		return getBeanList(Department.class, getSql("zz01.04"));
	}
	
	/**
	 * Employeeを更新する
	 * @param emp
	 * @return 対象件数
	 */
	public int update(Employee emp) {
		return update(null, emp);
	}

    /**
     * Employeeを更新する
     * (トランザクション管理も行う)
     * 
     * @param conn トランザクションを開始したConnection
     * @param emp
     * @return 対象件数
     */
    public int update(Connection conn, Employee emp) {
        Parameter params = new Parameter(
                emp.getFirst_name(),
                emp.getLast_name(),
                emp.getEmail(),
                emp.getPhone_number(),
                emp.getHire_date(),
                emp.getJob_id(),
                emp.getSalary(),
                emp.getCommission_pct(),
                emp.getManager_id(),
                emp.getDepartment_id(),
                emp.getEmployee_id()
            );
        return update(conn, getSql("zz01.05"), params.toObjectArray());
    }
    
    /**
     * Employeeを新規登録する
     * 
     * @param emp
     * @return 対象件数
     */
    public int insert(Employee emp) {
    	return insert(null, emp);
    }

    /**
     * Employeeを新規登録する
     * (トランザクション管理も行う)
     * 
     * @param conn トランザクションを開始したConnection
     * @param emp
     * @return 対象件数
     */
    public int insert(Connection conn, Employee emp) {
        Parameter params = new Parameter(
        		emp.getEmployee_id(),
                emp.getFirst_name(),
                emp.getLast_name(),
                emp.getEmail(),
                emp.getPhone_number(),
                emp.getHire_date(),
                emp.getJob_id(),
                emp.getSalary(),
                emp.getCommission_pct(),
                emp.getManager_id(),
                emp.getDepartment_id()
            );
        return update(conn, getSql("zz01.06"), params.toObjectArray());
        
    }    
    /**
     * Employeeを削除する
     * 
     * @param employeeId
     * @return 対象件数
     */
    public int delete(Integer employeeId) {
        return update(getSql("zz01.07"), Parameter.makeObjectArray(employeeId));
    }
}
