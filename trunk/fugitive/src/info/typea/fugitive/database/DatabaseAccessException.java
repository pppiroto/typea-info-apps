package info.typea.fugitive.database;

/**
 * Database�֘A�̗�O
 * <br/>
 * @author totec yagi
 */
public class DatabaseAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Throwable rootCause;
	
	/**
	 * �R���X�g���N�^
	 */
	public DatabaseAccessException() {
	}
	/**
     * �R���X�g���N�^
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
