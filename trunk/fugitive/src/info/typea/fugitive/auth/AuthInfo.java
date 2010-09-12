package info.typea.fugitive.auth;

import info.typea.fugitive.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ���[�U�F�؏���ێ�����
 * 
 * @author totec yagi
 */
public class AuthInfo {
	/**
	 * �Z�b�V�����A���N�G�X�g���̊i�[���̃L�[
	 */
	public static final String AUTH_INFO_KEY = AuthInfo.class.getName();

	/**
	 * �F�؃��[�U��
	 */
	private String userName;

	/**
	 * �F�؃p�X���[�h
	 */
	private String password;

	/**
	 * �F�ؐ���
	 */
	private boolean authorized;

	/**
	 * ���[�U�[���[���̃��X�g
	 */
	protected List<String> roleList;
	
	/**
	 * �R���X�g���N�^
	 * @param isAuthorized �F�ؐ���
	 * @param userName ���[�U��
	 * @param password �p�X���[�h
	 */
	public AuthInfo(boolean isAuthorized, String userName, String password) {
		this.authorized = isAuthorized;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * �R���X�g���N�^
	 * @param isAuthorized �F�ؐ���
	 */
	public AuthInfo(boolean isAuthorized) {
		this(isAuthorized, "", "");
	}
	
	/**
	 * �R���X�g���N�^
	 */
	public AuthInfo() {
		this(false, "", "");
	}

	/**
	 * �F�؎��p�X���[�h���擾����
	 * @return �F�؎��p�X���[�h
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * �F�؎��p�X���[�h��ݒ肷��
	 * @param password �F�؎��p�X���[�h
	 */
	public void setPassword(String password) {
		this.password = StringUtil.nvl(password);
	}

	/**
	 * �F�؎����[�U�����擾����
	 * @return �F�؎����[�U��
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * �F�؎����[�U����ݒ肷��
	 * @param userName �F�؎����[�U��
	 */
	public void setUserName(String userName) {
		this.userName = StringUtil.nvl(userName);
	}
	
	/**
	 * �L���b�V�����ꂽ���ƁA����̃��[�U���A�p�X���[�h����v���邩
	 * @param userName ���[�U��
	 * @param password �p�X���[�h
	 * @return ��v����ꍇtrue
	 */
	public boolean isMatchIdentity(String userName, String password) {
		return this.getUserName().equals(StringUtil.nvl(userName))
				&& this.getPassword().equals(StringUtil.nvl(password));
	}
	
	/**
	 * �F�ؐ��ۂ̎擾
	 * @return �F�ؐ���
	 */
	public boolean isAuthorized() {
		return authorized;
	}

	/**
	 * �F�ؐ��ۂ̐ݒ�
	 * @param authorized �F�ؐ���
	 */
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	
	/**
	 * ���[�U���[���̃��X�g���擾
	 * @return ���[�U�[���[���̃��X�g
	 */
	public List<String> getRoleList() {
		if (this.roleList == null) {
			this.roleList = new ArrayList<String>();
		}
		return roleList;
	}
	
	/**
	 * ���[�U�[���[���̃��X�g���Z�b�g
	 * @param roleList ���[�U�[���[���̃��X�g
	 */
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	
	/**
	 * ���[�U�[���[����ǉ�����
	 * @param role ���[�U�[���[��
	 */
	public void addRole(String role) {
		getRoleList().add(role);
	}
	
	/**
	 * �w�肳�ꂽ���[���ɑ����Ă��邩
	 * @param role ���[�U�[���[��
	 * @return �����Ă���ꍇtrue
	 */
	public boolean isInRole(String role) {
		return getRoleList().contains(role);
	}

	@Override
	public String toString() {
		return   "user:" + this.userName
		       + ", authorized:" + ((this.authorized)?"yes":"no")
		       + ", role:" + Arrays.toString(getRoleList().toArray());
	}
}
