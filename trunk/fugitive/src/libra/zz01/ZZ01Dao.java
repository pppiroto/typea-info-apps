package libra.zz01;

import info.typea.fugitive.database.DefaultDao;
import info.typea.fugitive.database.Parameter;
import info.typea.fugitive.util.StringUtil;

import java.sql.Connection;
import java.util.List;

import libra.zz01.model.Department;
import libra.zz01.model.Employee;


/**
 * �Ɩ��A�v���P�[�V�����p�f�[�^�A�N�Z�X�N���X(�T���v���v���O����)
 * 
 * @author totec yagi
 */
public class ZZ01Dao extends DefaultDao {
	public ZZ01Dao(){
		super("jdbc.datasource.hr");
	}
	/**
	 * Employee�̈ꗗ����������
	 * @param param
	 * @return �d���������������̃��X�g
	 */
	public List findEmployees(Parameter param) {
		String salaryFrom = param.get(4).toString();
		String salaryTo   = param.get(5).toString();
		String sql = null;

		// salary �����̗L���ɂ��A�g�p����SQL��ύX
		Object[] params = null;
		if (StringUtil.isBlank(salaryFrom) && StringUtil.isBlank(salaryTo)) {
			sql = "zz01.01";
			params = param.toObjectArray(3);
		} else {
			sql = "zz01.02";
			params = param.toObjectArray();
		}
		
		// �w������ŁAEmployee�̃��X�g���擾����
		return getBeanList(Employee.class, getSql(sql), params);
	}
	
	/**
	 * Employee����������
	 * @param employeeId
	 * @return Employee
	 */
	public Employee findEmployee(String employeeId) {
		return (Employee) getBean(Employee.class, getSql("zz01.03"), 
							Parameter.makeObjectArray(employeeId));
	}
	/**
	 * Department�̈ꗗ����������
	 * @return Department�̃��X�g
	 */
	public List findDepartments() {
		return getBeanList(Department.class, getSql("zz01.04"));
	}
	
	/**
	 * Employee���X�V����
	 * @param emp
	 * @return �Ώی���
	 */
	public int update(Employee emp) {
		return update(null, emp);
	}

    /**
     * Employee���X�V����
     * (�g�����U�N�V�����Ǘ����s��)
     * 
     * @param conn �g�����U�N�V�������J�n����Connection
     * @param emp
     * @return �Ώی���
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
     * Employee��V�K�o�^����
     * 
     * @param emp
     * @return �Ώی���
     */
    public int insert(Employee emp) {
    	return insert(null, emp);
    }

    /**
     * Employee��V�K�o�^����
     * (�g�����U�N�V�����Ǘ����s��)
     * 
     * @param conn �g�����U�N�V�������J�n����Connection
     * @param emp
     * @return �Ώی���
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
     * Employee���폜����
     * 
     * @param employeeId
     * @return �Ώی���
     */
    public int delete(Integer employeeId) {
        return update(getSql("zz01.07"), Parameter.makeObjectArray(employeeId));
    }
}
