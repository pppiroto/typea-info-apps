package libra.zz00;

import java.util.List;

import libra.zz00.model.Role;

import info.typea.fugitive.database.DefaultDao;
import info.typea.fugitive.database.Parameter;

public class ZZ00Dao extends DefaultDao {
	public ZZ00Dao(){
		super("jdbc.datasource.fugitive");
	}
	public List findRoles(String username, String passwd) {
		
		return getBeanList(Role.class, getSql("zz00.userroles"), 
				Parameter.makeObjectArray(username, passwd));
	}
}
