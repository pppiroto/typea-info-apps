package libra.zz01;

import info.typea.fugitive.action.BeanActionForm;
import info.typea.fugitive.action.SelectOptionBean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import libra.zz01.model.Department;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 * Employee データ更新用Formクラス(サンプルプログラム)
 * 
 * @author totec yagi
 */
public class ZZ01_02Form extends BeanActionForm {
	private static final long serialVersionUID = 1L;
	
	/**
	 * departments セレクトボックス表示用
	 */
	private List<SelectOptionBean> departments;
	
    private String employee_id;
    private FormFile upfile;
    
	/* @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest) */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setUpfile(null);
    }

	/**
	 * @return Departmentのリスト
	 */
	public List getDepartments() {
		return departments;
	}

    /**
	 * @param departments
	 */
	public void setDepartments(List departments) {
        this.departments = new ArrayList<SelectOptionBean>();
        for (int i=0; i<departments.size(); i++) {
            Department dep = (Department)departments.get(i);
            SelectOptionBean option = new SelectOptionBean();
            option.setLabel(dep.getDepartment_name());
            option.setValue(dep.getDepartment_id().toString());
            this.departments.add(option);
        }
	}

    /**
     * @return Returns the employee_id.
     */
    public String getEmployee_id() {
        return employee_id;
    }
    /**
     * @param employee_id The employee_id to set.
     */
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    /**
     * @return Returns the upfile.
     */
    public FormFile getUpfile() {
        return upfile;
    }

    /**
     * @param upfile The upfile to set.
     */
    public void setUpfile(FormFile upfile) {
        this.upfile = upfile;
    }
}
