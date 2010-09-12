package info.typea.fugitive.database;

/**
 * Database関連の例外
 * <br/>
 * @author totec yagi
 */
public class DatabaseAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Throwable rootCause;
	
	/**
	 * コンストラクタ
	 */
	public DatabaseAccessException() {
	}
	/**
     * コンストラクタ
	 * @param t
	 */
	public DatabaseAccessException(Throwable rootCause) {
        super(rootCause.getLocalizedMessage());
        this.rootCause = rootCause;
	}

    public Throwable getRootCause()
    {
        return rootCause;
    }
}
