package libra.al01;

import info.typea.fugitive.database.DefaultDao;
import info.typea.fugitive.database.Parameter;

import java.util.List;

import libra.al01.model.Path;
import libra.al01.model.Role;

public class AL01Dao extends DefaultDao {
	public AL01Dao(){
		super("jdbc.datasource.fugitive");
	}
	public List findRoles(String username, String passwd) {
		return getBeanList(Role.class, getSql("al01.01"), 
				Parameter.makeObjectArray(username, passwd));
	}
	public List findPaths(String username) {
		return getBeanList(Path.class, getSql("al01.02"),
				Parameter.makeObjectArray(username));
	}
}
