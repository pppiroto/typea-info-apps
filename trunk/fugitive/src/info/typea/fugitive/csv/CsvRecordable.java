package info.typea.fugitive.csv;

/**
 * インスタンスを、CSVレコードに変換可能とする。
 * @author totec yagi
 */
public interface CsvRecordable {
	/**
	 * インスタンスを文字列配列で表現する
	 * @return 文字列配列として表現されたインスタンス
	 */
	String[] toStringArray();
	
	/**
	 * インスタンスをCSV文字列で表現する
	 * @return CSV文字列として表現されたインスタンス
	 */
	String   toCsvRecord();
}
