package info.typea.fugitive.database;

/**
 * JNDI����Datasource���擾����Dao�N���X
 * <br/>
 * �T�[�u���b�g�R���e�i�ŊǗ�����Ă���Datasource���A�擾���邱�Ƃ�z�肵�Ă���B
 * �R���e�i���̐ݒ�� {@link JNDIDatasourceFactory}���Q�ƁB
 * 
 * @see JNDIDatasourceFactory
 * @author totec yagi
 */
public class DefaultDao extends Dao {
	/**
	 * �R���X�g���N�^
	 * @param datasourceFactory �c������������������
	 */
	private DefaultDao(DatasourceFactory datasourceFactory) {
		super(datasourceFactory);
	}
	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public DefaultDao() {
		this(new JNDIDatasourceFactory());
	}
	
	/**
	 * �R���X�g���N�^
	 * JNDI���\�[�X�L�[�}�b�s���O�t�@�C���̃L�[���w�肷��
	 * @param dictionaryKey 
	 */
	public DefaultDao(String dictionaryKey) {
		this(new JNDIDatasourceFactory(dictionaryKey));
	}
}
