package info.typea.fugitive.csv;

/**
 * �C���X�^���X���ACSV���R�[�h�ɕϊ��\�Ƃ���B
 * @author totec yagi
 */
public interface CsvRecordable {
	/**
	 * �C���X�^���X�𕶎���z��ŕ\������
	 * @return ������z��Ƃ��ĕ\�����ꂽ�C���X�^���X
	 */
	String[] toStringArray();
	
	/**
	 * �C���X�^���X��CSV������ŕ\������
	 * @return CSV������Ƃ��ĕ\�����ꂽ�C���X�^���X
	 */
	String   toCsvRecord();
}
