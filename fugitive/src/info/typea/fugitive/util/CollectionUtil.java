package info.typea.fugitive.util;

/**
 * �R���N�V��������p���[�e�B���e�B
 * <br/>
 * @author totec yagi
 */
public final class CollectionUtil {
	/**
	 * �R���X�g���N�^
	 */
	private CollectionUtil(){}
	
	/**
	 * �z�񂩂�̒l���o�����ɁA�͈̓`�F�b�N�ƃf�t�H���g�l�̎w����s���B
	 * @param array �Ώۂ̔z��
	 * @param index ���o���C���f�b�N�X
	 * @param defaultValue �w��C���f�b�N�X�ł͎��o���Ȃ��ꍇ�̃f�t�H���g�l
	 * @return �z�񂩂���o�����l
	 */
	public static <T extends Object> T safeArrayElement(T[] array, int index, T defaultValue) {
		if (array == null || array.length <= index) {
			return defaultValue;
		}
		return array[index];
	}
}
