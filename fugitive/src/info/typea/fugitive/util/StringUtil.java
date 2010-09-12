package info.typea.fugitive.util;

/**
 * 文字列操作用ユーティリティ
 * <br/>
 * @author totec yagi
 */
public final class StringUtil {
	/**
	 * コンストラクタ
	 */
	private StringUtil() {}

	/**
	 * 文字列のブランク判定
	 * @param str
	 * @return 文字列がnullまたは空文字ならtrue
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 引数が null の場合空文字、それ以外は引数の文字列をそのまま返す
	 * @param str 
	 * @return 引数が null の場合空文字、それ以外は引数の文字列
	 */
	public static String nvl(String str) {
		if (str == null) return "";
		return str;
	}
}
