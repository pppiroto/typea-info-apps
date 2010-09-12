package info.typea.fugitive.service;

import info.typea.fugitive.logging.LogUtil;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * JNDI���\�[�X����������L�[�ɂ��āA�v���O�����Ŏw�肷��l��JNDI����������l�Ƃ�
 * �}�b�s���O�����ݒ�t�@�C���̒l��ێ�����B
 * 
 * @author totec yagi
 */
public final class ServiceDictionary {
	/**
	 * JNDI���\�[�XKey�̒l���`�����v���p�e�B�t�@�C���� 
	 */
	public static final String PROP_FILE_NAME = "service_dictionary.properties";
	
	/**
	 * ���O�o�͗p�N���X
	 */
	protected Logger log = LogUtil.getLogger(ServiceDictionary.class);
	
	/**
	 * Singleton�p�C���X�^���X�ێ�
	 */
	private static ServiceDictionary me;
	
	/**
	 * JNDI���\�[�XKey�̒l���`�����v���p�e�B 
	 */
	private Properties prop = null;
	
	/**
	 * �R���X�g���N�^
	 */
	private ServiceDictionary(){
		loadProperties();
	}

	/**
	 * Singleton�Ƃ��ăC���X�^���X���擾
	 * @return
	 */
	public static ServiceDictionary getInstance() {
		if (me == null) {
			me = new ServiceDictionary();
		}
		return me;
	}

	/**
	 * JNDI���\�[�XKey�̒l���`�����v���p�e�B�t�@�C����Ǎ���
	 */
	private synchronized void loadProperties() {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader == null) {
				getClass().getClassLoader();
			}
			InputStream in = loader.getResourceAsStream(PROP_FILE_NAME);
			prop = new Properties();
			prop.load(in);
			
			if (log.isDebugEnabled()) {
				Enumeration keys = prop.keys();
				log.debug("loading service dictionary.");
				int num = 1;
				while(keys.hasMoreElements()) {
					String key = (String)keys.nextElement();
					log.debug(String.valueOf(num++)+ ":" + key + ":" + prop.getProperty(key));
				}
			}
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * JNDI���\�[�XKey�Ƀv���p�e�B�t�@�C���őΉ��t�����L�[���w�肵�āAJNDI���\�[�XKey���擾����B
	 * @param dictionaryKey JNDI���\�[�XKey�ɉ��t�����L�[
	 * @return JNDI���\�[�XKey
	 */
	public String getResourceKeyName(String dictionaryKey) {
		String keyName = null;
		if (prop != null) {
			keyName = prop.getProperty(dictionaryKey);
		}
		return keyName;
	}
}
