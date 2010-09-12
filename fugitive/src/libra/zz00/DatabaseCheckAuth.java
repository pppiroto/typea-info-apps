package libra.zz00;

import info.typea.fugitive.auth.AuthInfo;
import info.typea.fugitive.auth.SessionCasheCheckAuth;

import java.util.Iterator;
import java.util.List;

import libra.zz00.model.Role;

public class DatabaseCheckAuth extends SessionCasheCheckAuth {

	public DatabaseCheckAuth() {
		this.setKeepCasheMinutes(20); // 20•ªƒLƒƒƒbƒVƒ…‚·‚é
	}
	public AuthInfo authorize(String username, String password) {
		boolean ret = false;
		AuthInfo result = new AuthInfo();
		
		ZZ00Dao dao = new ZZ00Dao();
		List roles = dao.findRoles(username, password);
		if (roles != null) {
			Iterator itr = roles.iterator();
			while (itr.hasNext()) {
				String role = ((Role)itr.next()).getRole();
				if ("manager".equalsIgnoreCase(role)
				  || "admin".equalsIgnoreCase(role)
				  || "administrator".equalsIgnoreCase(role)) {
					ret = true;
				}
				result.addRole(role);
			}
		}
		result.setAuthorized(ret);
		
        return (result);
    }
}
