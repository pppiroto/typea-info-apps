package info.typea.fugitive.util;

/**
 * �����񑀍�p���[�e�B���e�B
 * <br/>
 * @author totec yagi
 */
public final class StringUtil {
	/**
	 * �R���X�g���N�^
	 */
	private StringUtil() {}

	/**
	 * ������̃u�����N����
	 * @param str
	 * @return ������null�܂��͋󕶎��Ȃ�true
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * ������ null �̏ꍇ�󕶎��A����ȊO�͈����̕���������̂܂ܕԂ�
	 * @param str 
	 * @return ������ null �̏ꍇ�󕶎��A����ȊO�͈����̕�����
	 */
	public static String nvl(String str) {
		if (str == null) return "";
		return str;
	}
}
