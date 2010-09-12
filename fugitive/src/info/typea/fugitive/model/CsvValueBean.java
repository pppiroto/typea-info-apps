package info.typea.fugitive.model;

import info.typea.fugitive.csv.CsvRecordable;
import info.typea.fugitive.csv.CsvUtil;

/**
 * CSVとして表現可能な、値オブジェクト
 * <br/>
 * @author totec yagi
 */
public abstract class CsvValueBean extends ValueBean implements CsvRecordable{

	/* (non-Javadoc)
	 * @see fugitive.fw.csv.CsvRecordable#toCsvRecord()
	 */
	public String toCsvRecord() {
		return CsvUtil.toCsvRecord(this.toStringArray());
	}

	/* (non-Javadoc)
	 * @see fugitive.fw.csv.CsvRecordable#toStringArray()
	 */
	public String[] toStringArray() {
		return CsvUtil.toStringArray(getFieldData());	
	}

	/**
	 * CSVデータとして表現するフィールドを、Object配列として返す。
	 * @return CSVデータとして表現するフィールド配列
	 */
	public abstract Object[] getFieldData();
}
