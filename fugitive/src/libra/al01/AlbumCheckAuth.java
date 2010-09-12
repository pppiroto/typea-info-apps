package libra.al01;

import info.typea.fugitive.auth.AuthInfo;
import info.typea.fugitive.auth.SessionCasheCheckAuth;

import java.util.List;

import libra.al01.model.AlbumAuthInfo;
import libra.al01.model.Path;
import libra.al01.model.Role;

public class AlbumCheckAuth extends SessionCasheCheckAuth {
	
	@Override
	public String getSessionKey() {
		return AlbumAuthInfo.SESSION_KEY;
	}

	@Override
	public AuthInfo authorize(String username, String password) {
		
		AlbumAuthInfo ai = new AlbumAuthInfo();
		ai.setUserName(username);
		ai.setPassword(password);
		
		AL01Dao dao = new AL01Dao();
		
		List roles = dao.findRoles(username, password);
		if (roles != null && roles.size() > 0) {
			for(int i=0; i<roles.size(); i++) {	
				 ai.addRole(((Role)roles.get(i)).getRole());
			}
			List paths = dao.findPaths(username);
			if (paths != null) {
				for(int i=0; i<paths.size(); i++) {
					ai.addPath(((Path)paths.get(i)).getPath());
				}
			}
			ai.setAuthorized(true);
		} 
		return ai;
	}
}
