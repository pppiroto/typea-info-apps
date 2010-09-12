package info.typea.fugitive.util;

/**
 * コレクション操作用ユーティリティ
 * <br/>
 * @author totec yagi
 */
public final class CollectionUtil {
	/**
	 * コンストラクタ
	 */
	private CollectionUtil(){}
	
	/**
	 * 配列からの値取り出し時に、範囲チェックとデフォルト値の指定を行う。
	 * @param array 対象の配列
	 * @param index 取り出すインデックス
	 * @param defaultValue 指定インデックスでは取り出せない場合のデフォルト値
	 * @return 配列から取り出した値
	 */
	public static <T extends Object> T safeArrayElement(T[] array, int index, T defaultValue) {
		if (array == null || array.length <= index) {
			return defaultValue;
		}
		return array[index];
	}
}
