package info.typea.fugitive.csv;

/**
 * CSVデータ用ユーティリティ
 * @author totec yagi
 */
public class CsvUtil {
	
	/**
	 * CSVデータフィールド区切文字 
	 */
	public static final String CSV_SEP = ",";

	/**
	 * コンストラクタ
	 */
	private CsvUtil() {}
	
	/**
	 * 可変引数として受け取ったObjectをObject配列として返す
	 * @param fields 可変引数としてオブジェクトを渡す 
	 * @return 可変引数として渡されたObject配列
	 */
	public static String[] toStringArray(Object... fields) {
		int size = 0;
		if (fields != null) {
			size = fields.length;
		}
		String[] result = new String[size];
		
		for (int i=0; i<size; i++) {
			result[i] = String.valueOf(fields[i]);
		}
		return result;
	}
	
	/**
	 * 可変引数として渡された文字列から、CSVレコード文字列を作成する
	 * @param fields CSVのフィールド
	 * @return CSVレコード
	 */
	public static String toCsvRecord(String... fields) {
		StringBuilder buf = new StringBuilder();
		if (fields != null) {
			for (int i=0; i<fields.length; i++) {
				if (i != 0) {
					buf.append(CSV_SEP);
				}
				buf.append(enclose(fields[i]));
			}
		}
		return buf.toString();
	}
	
	/**
	 * フィールドを二重引用符で囲んで返す
	 * @param field フィールド
	 * @return 二重引用符でかこまれたフィールド
	 */
	public static String enclose(String field) {
		return "\"" + field + "\"";
	}
}
