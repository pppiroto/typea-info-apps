package info.typea.fugitive.service;

/**
 * ServiceLocator関連の例外
 * <br/>
 * @author totec yagi
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/**
	 * コンストラクタ
	 */
	public ServiceException() {
	}
	/**
     * コンストラクタ
	 * @param t
	 */
	public ServiceException(Throwable t) {
		super(t);
	}
}
