package libra.zz01;

import info.typea.fugitive.database.DatabaseAccessException;
import info.typea.fugitive.database.Parameter;
import info.typea.fugitive.logic.ApplicationLogic;
import info.typea.fugitive.logic.Message;
import info.typea.fugitive.logic.Messages;
import info.typea.fugitive.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;

import libra.zz01.model.Employee;


/**
 * �Ɩ����W�b�N�N���X(�T���v���N���X)
 * @author totec yagi
 */
public class ZZ01Logic extends ApplicationLogic {
    /**
     * �X�V���A�V�K�o�^�����敪����
     */
    public enum UPDATE_TYPE {INSERT, UPDATE};
    
    /**
     * Employee�ꗗ����������
     * @param first_name
     * @param last_name
     * @param email
     * @param salary_from
     * @param salary_to
     * @return Employee�̃��X�g
     */
    public List findEmployees(String first_name,
                              String last_name,
                              String email,
                              String salary_from,
                              String salary_to) {
        
    	// SQL���������p�����[�^�𐶐�
        Parameter params = new Parameter();
        params.put(1, StringUtil.nvl(first_name) + "%");
        params.put(2, StringUtil.nvl(last_name)  + "%");
        params.put(3, StringUtil.nvl(email)      + "%");
        params.put(4, StringUtil.nvl(salary_from)     );
        params.put(5, StringUtil.nvl(salary_to)       );
        
        // �f�[�^�x�[�X�ɃA�N�Z�X���A���ʂ��擾
        return (new ZZ01Dao()).findEmployees(params);
    }

    /**
     * @param employee_id
     * @return Employee
     */
    public Employee findEmployee(String employee_id){
    	// �f�[�^�x�[�X�ɃA�N�Z�X���A���ʂ��擾
        return (new ZZ01Dao()).findEmployee(employee_id);
    }

    /**
     * �X�V���V�K�o�^���𔻒肵�A�������Ăяo��
     * @param type
     * @param emp
     * @return �Ώی���
     */
    public int insertOrUpdateEmployee(UPDATE_TYPE type, Employee emp) {
        ZZ01Dao dao = new ZZ01Dao();
        
        int ret = -1;
        switch (type) {
        case INSERT:
            ret = dao.insert(emp);
            break;
        case UPDATE:
            ret = dao.update(emp);
            break;
        default:
            throw new IllegalArgumentException();
        }
        return ret;
    }
    
    /**
     * Employee���폜����
     * @param employee_id
     * @return �Ώی���
     */
    public int deleteEmployee(String employee_id) {
        return (new ZZ01Dao()).delete(new Integer(employee_id));
    }
    /**
     * �V�KEmployee�C���X�^���X���擾����
     * @return �Ώی���
     */
    public Employee createEmployee() {
        return new Employee();
    }
    /**
     * Department�ꗗ����������
     * @return Department�̃��X�g
     */
    public List findDepartments() {
        return (new ZZ01Dao()).findDepartments();
    }
    
    /**
     * Employee�ꗗ��CSV�捞�������s��
     * @param csvInput
     * @return �Ώی���
     */
    public int importEmployeesFromCsvData(InputStream csvInput) throws Exception {
        int recCnt = 0;
        
        // CSV�f�[�^��Reader���쐬
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvInput));
        
        // �f�[�^�A�N�Z�X�N���X������
        ZZ01Dao dao = new ZZ01Dao();
        
        // �g�����U�N�V�����̊J�n
        Connection conn = dao.beginTransaction();
        
        String line = null;
        try {
        	// CSV���R�[�h�����݂���ԏ������s��
	        while((line = reader.readLine()) != null) {
	        	
	        	// �J���}�Ńt�B�[���h�𕪊�
	            String[] fields = line.split(",");
	         
	            // �擪�Ɩ����̓�d���p������菜��
	            for(int i=0; i<fields.length; i++) {
	                fields[i] = fields[i].replaceAll("^\"(.*)\"$", "$1");
	            }
	            
	            // Employee�C���X�^���X�̍쐬
	            Employee emp = createEmployee();
	
	            // CSV�̃t�B�[���h�f�[�^���C���X�^���X�ɃZ�b�g
	            emp.setEmployee_id(    fields[0] );
	            emp.setFirst_name(     fields[1] );
	            emp.setLast_name(      fields[2] );
	            emp.setEmail(          fields[3] );
	            emp.setPhone_number(   fields[4] );
	            emp.setHire_date(      fields[5] );
	            emp.setJob_id(         fields[6] );
	            emp.setSalary(         fields[7] );
	            emp.setCommission_pct( fields[8] );
	            emp.setManager_id(     fields[9] );
	            emp.setDepartment_id(  fields[10]);
	
	            // �V�K�o�^����
	            dao.insert(conn, emp);
	            
	            recCnt++;
	        }
	        
	        // �g�����U�N�V�����̃R�~�b�g
	        dao.commitTranzaction(conn);
	        
        } catch(Exception e) {
        	// ��O�����������ꍇ�A�g�����U�N�V�����̃��[���o�b�N���s��
        	dao.rollbackTranzaction(conn);
        	throw new DatabaseAccessException(e);
        } finally {
	        reader.close();
        }
        return recCnt;
    }
    
    /**
     * Employee �̑Ó����������s���B
     * (�T���v���̂��߁A��L�[�̌����̂ݎ��{)
     * 
     * @param errors
     * @param emp
     * @return �G���[���b�Z�[�W�ێ��N���X
     */
    public Messages validateEmployee(Messages errors, Employee emp) {
        if (errors == null) {
            errors = new Messages();
        }
        
        if (StringUtil.isBlank(emp.getEmployee_id())) {
            errors.add(new Message("errors.required", "EMPLOYEE ID"));
            errors.add(new Message("errors.invalid", "EMPLOYEE ID"));
        }
        return errors;
    }
}
