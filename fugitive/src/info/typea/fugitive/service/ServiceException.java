package info.typea.fugitive.service;

/**
 * ServiceLocator�֘A�̗�O
 * <br/>
 * @author totec yagi
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/**
	 * �R���X�g���N�^
	 */
	public ServiceException() {
	}
	/**
     * �R���X�g���N�^
	 * @param t
	 */
	public ServiceException(Throwable t) {
		super(t);
	}
}
