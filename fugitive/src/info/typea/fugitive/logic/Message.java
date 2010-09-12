package info.typea.fugitive.logic;

/**
 * �Ɩ����b�Z�[�W���Ǘ�����
 * @author totec yagi
 */
public class Message {
    /**
     * ���b�Z�[�W�L�[
     */
    private String key;
    /**
     * ���b�Z�[�W�̃v���[�X�t�H���_��u��������p�����[�^
     */
    private Object[] parameters;
    
    /**
     * �R���X�g���N�^
     * @param key ���b�Z�[�W�L�[
     * @param parameters ���b�Z�[�W�̃v���[�X�t�H���_��u��������p�����[�^
     */
    public Message(String key, Object...parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    /**
     * �R���X�g���N�^
     * @param key ���b�Z�[�W�L�[
     */
    public Message(String key) {
        this(key, new Object[]{});
    }
    
    /**
     * ���b�Z�[�W�L�[���擾����
     * @return ���b�Z�[�W�L�[
     */
    public String getKey() {
        return key;
    }
    
    /**
     * ���b�Z�[�W�L�[���Z�b�g����
     * @param key ���b�Z�[�W�L�[
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * �p�����[�^���擾����
     * @return �p�����[�^
     */
    public Object[] getParameters() {
        return parameters;
    }
    /**
     * �p�����[�^���Z�b�g����
     * @param parameters �p�����[�^
     */
    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
