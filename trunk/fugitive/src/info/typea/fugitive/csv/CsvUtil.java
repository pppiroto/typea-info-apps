package info.typea.fugitive.csv;

/**
 * CSV�f�[�^�p���[�e�B���e�B
 * @author totec yagi
 */
public class CsvUtil {
	
	/**
	 * CSV�f�[�^�t�B�[���h��ؕ��� 
	 */
	public static final String CSV_SEP = ",";

	/**
	 * �R���X�g���N�^
	 */
	private CsvUtil() {}
	
	/**
	 * �ψ����Ƃ��Ď󂯎����Object��Object�z��Ƃ��ĕԂ�
	 * @param fields �ψ����Ƃ��ăI�u�W�F�N�g��n�� 
	 * @return �ψ����Ƃ��ēn���ꂽObject�z��
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
	 * �ψ����Ƃ��ēn���ꂽ�����񂩂�ACSV���R�[�h��������쐬����
	 * @param fields CSV�̃t�B�[���h
	 * @return CSV���R�[�h
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
	 * �t�B�[���h���d���p���ň͂�ŕԂ�
	 * @param field �t�B�[���h
	 * @return ��d���p���ł����܂ꂽ�t�B�[���h
	 */
	public static String enclose(String field) {
		return "\"" + field + "\"";
	}
}
