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
 * 業務ロジッククラス(サンプルクラス)
 * @author totec yagi
 */
public class ZZ01Logic extends ApplicationLogic {
    /**
     * 更新か、新規登録かを区分する
     */
    public enum UPDATE_TYPE {INSERT, UPDATE};
    
    /**
     * Employee一覧を検索する
     * @param first_name
     * @param last_name
     * @param email
     * @param salary_from
     * @param salary_to
     * @return Employeeのリスト
     */
    public List findEmployees(String first_name,
                              String last_name,
                              String email,
                              String salary_from,
                              String salary_to) {
        
    	// SQL検索条件パラメータを生成
        Parameter params = new Parameter();
        params.put(1, StringUtil.nvl(first_name) + "%");
        params.put(2, StringUtil.nvl(last_name)  + "%");
        params.put(3, StringUtil.nvl(email)      + "%");
        params.put(4, StringUtil.nvl(salary_from)     );
        params.put(5, StringUtil.nvl(salary_to)       );
        
        // データベースにアクセスし、結果を取得
        return (new ZZ01Dao()).findEmployees(params);
    }

    /**
     * @param employee_id
     * @return Employee
     */
    public Employee findEmployee(String employee_id){
    	// データベースにアクセスし、結果を取得
        return (new ZZ01Dao()).findEmployee(employee_id);
    }

    /**
     * 更新か新規登録かを判定し、処理を呼び出す
     * @param type
     * @param emp
     * @return 対象件数
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
     * Employeeを削除する
     * @param employee_id
     * @return 対象件数
     */
    public int deleteEmployee(String employee_id) {
        return (new ZZ01Dao()).delete(new Integer(employee_id));
    }
    /**
     * 新規Employeeインスタンスを取得する
     * @return 対象件数
     */
    public Employee createEmployee() {
        return new Employee();
    }
    /**
     * Department一覧を検索する
     * @return Departmentのリスト
     */
    public List findDepartments() {
        return (new ZZ01Dao()).findDepartments();
    }
    
    /**
     * Employee一覧のCSV取込処理を行う
     * @param csvInput
     * @return 対象件数
     */
    public int importEmployeesFromCsvData(InputStream csvInput) throws Exception {
        int recCnt = 0;
        
        // CSVデータのReaderを作成
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvInput));
        
        // データアクセスクラスを準備
        ZZ01Dao dao = new ZZ01Dao();
        
        // トランザクションの開始
        Connection conn = dao.beginTransaction();
        
        String line = null;
        try {
        	// CSVレコードが存在する間処理を行う
	        while((line = reader.readLine()) != null) {
	        	
	        	// カンマでフィールドを分割
	            String[] fields = line.split(",");
	         
	            // 先頭と末尾の二重引用符を取り除く
	            for(int i=0; i<fields.length; i++) {
	                fields[i] = fields[i].replaceAll("^\"(.*)\"$", "$1");
	            }
	            
	            // Employeeインスタンスの作成
	            Employee emp = createEmployee();
	
	            // CSVのフィールドデータをインスタンスにセット
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
	
	            // 新規登録処理
	            dao.insert(conn, emp);
	            
	            recCnt++;
	        }
	        
	        // トランザクションのコミット
	        dao.commitTranzaction(conn);
	        
        } catch(Exception e) {
        	// 例外が発生した場合、トランザクションのロールバックを行う
        	dao.rollbackTranzaction(conn);
        	throw new DatabaseAccessException(e);
        } finally {
	        reader.close();
        }
        return recCnt;
    }
    
    /**
     * Employee の妥当性検査を行う。
     * (サンプルのため、主キーの検査のみ実施)
     * 
     * @param errors
     * @param emp
     * @return エラーメッセージ保持クラス
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
