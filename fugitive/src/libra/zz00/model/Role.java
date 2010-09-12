package libra.zz00.model;

import info.typea.fugitive.model.ValueBean;

public class Role extends ValueBean {
	private static final long serialVersionUID = 1L;

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
