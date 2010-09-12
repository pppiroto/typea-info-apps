package info.typea.fugitive.model;

import info.typea.fugitive.csv.CsvRecordable;
import info.typea.fugitive.csv.CsvUtil;

/**
 * CSV�Ƃ��ĕ\���\�ȁA�l�I�u�W�F�N�g
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
	 * CSV�f�[�^�Ƃ��ĕ\������t�B�[���h���AObject�z��Ƃ��ĕԂ��B
	 * @return CSV�f�[�^�Ƃ��ĕ\������t�B�[���h�z��
	 */
	public abstract Object[] getFieldData();
}
