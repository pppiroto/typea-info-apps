package info.typea.fugitive.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 * JNDI���\�[�X����̃T�[�r�X�擾���J�v�Z��������N���X
 * 
 * @author totec yagi
 */
public class ServiceLocator {
	/**
	 * Singleton�p�C���X�^���X
	 */
	private static ServiceLocator me = null;
	/**
	 * �l�[�~���O�R���e�L�X�g
	 */
	private InitialContext context = null;
	
	/**
	 * �R���X�g���N�^
	 */
	private ServiceLocator(){}
	
	/**
	 * Singleton�Ƃ��ẴC���X�^���X���擾
	 * @return
	 */
	public static ServiceLocator getInstance(){
		if (me == null) {
			me = new ServiceLocator();
		}
		return me;
	}

	/**
	 * �l�[�~���O�R���e�L�X�g���擾
	 * @return �l�[�~���O�R���e�L�X
	 */
	private InitialContext getContext() {
		if (context == null) {
			try {
				context = new InitialContext();
			} catch (NamingException e) {
				throw new ServiceException(e);
			}
		}
		return context;
	}
	
	/**
	 * JNDI���\�[�X����w�肳�ꂽ�L�[�ɑΉ�����T�[�r�X���擾����B
	 * @param key JNDI���\�[�X�L�[ 
	 * @return �w�肳�ꂽ�L�[�ɑΉ�����T�[�r�X
	 */
	public Object getService(String key) {
		Object ret = null;
		try {
			ret = getContext().lookup(key);
		} catch (NamingException e) {
			throw new ServiceException(e);
		}
		return ret;
	}
}
