package info.typea.fugitive.database;

import java.util.TreeMap;

/**
 * �C���f�b�N�X�t����SQL���p�p�����[�^���Ǘ�����B
 * <br/>
 * �C���f�b�N�X�� 1 ����J�n
 * 
 * @author totec yagi
 */
public class Parameter {
	/**
	 * �p�����[�^���Ǘ�����Map
	 */
	protected TreeMap<Integer, Object> paramMap = new TreeMap<Integer, Object>();
	
	/**
	 * �R���X�g���N�^
	 */
	public Parameter() {}
	
	/**
     * �R���X�g���N�^
     * �p�����[�^�z�񂩂�A�C���X�^���X�𐶐�����
	 * @param params �p�����[�^�z��
	 */
	public Parameter(Object...params) {
		int idx = 1;
		for (Object param : params) {
			paramMap.put(idx++, param);
		}
	}
	/**
     * �w�肳�ꂽ�C���f�b�N�X�Ƀp�����[�^��}������B
	 * @param index �C���f�b�N�X
	 * @param param �p�����[�^
	 */
	public void put(int index, Object param) {
		if (index < 1) {
			throw new IllegalArgumentException();
		}
		paramMap.put(index, param);
	}
    
	/**
     * �w�肳�ꂽ�C���f�b�N�X�̃p�����[�^���擾����B
	 * @param index �C���f�b�N�X
	 * @return �p�����[�^
	 */
	public Object get(int index) {
		return paramMap.get(index);
	}
    
	/**
	 * �ێ�����p�����[�^�̌㔼�̈ꕔ��؂�̂Ă�
	 * @param max �擾����p�����[�^Object[] �̌���
	 * @return �㔼�̈ꕔ��؂�̂Ă��p�����[�^�z��
	 */
	public Object[] toObjectArray(int max) {
		int size = paramMap.lastKey();
		if (max > 0) {
			size = Math.min(size, max);
		}
		
		Object[] params = new Object[size];
		for (int i=1; i<=size; i++) {
			if (paramMap.containsKey(i)) {
				params[i-1] = paramMap.get(i);
			} else {
				params[i-1] = "";
			}
		}
		return params;
	}
    
	/**
     * �ێ�����p�����[�^��Object�̔z��Ƃ��ĕԂ��B
	 * @return �p�����[�^�z��
	 */
	public Object[] toObjectArray() {
		return toObjectArray(-1);
	}
    
	/**
     * �ψ����Ƃ��ēn���ꂽ�A�p�����[�^���A�I�u�W�F�N�g�̔z��Ƃ��ĕԂ��B
	 * @param params �p�����[�^�z��
	 * @return �p�����[�^�I�u�W�F�N�g�̔z��
	 */
	public static Object[] makeObjectArray(Object...params) {
		return params;
	}
}
