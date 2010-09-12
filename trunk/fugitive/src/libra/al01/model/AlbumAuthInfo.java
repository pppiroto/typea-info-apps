package libra.al01.model;

import info.typea.fugitive.auth.AuthInfo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class AlbumAuthInfo extends AuthInfo {
	/**
	 * îFèÿåãâ Çäiî[Ç∑ÇÈÉLÅ[
	 */
	public static final String SESSION_KEY = AlbumAuthInfo.class.getName();
	
	private List<String> pathList;
	
	public AlbumAuthInfo() {
		this(false, "", "");
	}
	
	public AlbumAuthInfo(boolean isAuthorized, String userName, String password) {
		super(isAuthorized, userName, password);
	}

	public void addPath(String path) {
		if (pathList == null) {
			pathList = new ArrayList<String>();
		}
		pathList.add(path);
	}
	public List<String> getPathList() {
		return pathList;
	}

	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}
}
