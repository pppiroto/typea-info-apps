package info.typea.fugitive.database;

import info.typea.fugitive.logging.LogUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

/**
 * �Ɩ��A�v���P�[�V��������̃f�[�^�A�N�Z�X���J�v�Z��������
 * <br/>
 * SQL���v���p�e�B�t�@�C������Ǎ��A�C���X�^���X�ŕێ����邱�Ƃ��ł���B
 * SQL�́APreparedStatement �Ŏg�p����ꍇ��SQL�Ɠ��l�ɁASQL������"?"���v���[�X�z���_�[�Ƃ��āA
 * �����Ƀp�����[�^�����������ʂƂ��Ă�SQL�����s�����B
 * <br/>
 * �g�����U�N�V�������g�p����ꍇ�A�e�f�[�^�擾�A�X�V���\�b�h�̂����A������java.sql.Connection ���Ƃ���̂��g�p����B
 * �����Ƃ��� Connection��n���O�ɁAConnection�̎����R�~�b�g���[�h��OFF�ɂ��Ă����B
 * beginTransaction()���\�b�h�ɂ��AConnection�̎����R�~�b�g���[�h��OFF�Ƃ���Connection���擾���邱�Ƃ��ł���B
 * <br/>
 * ��{�I�Ȏg�p�@�́A
 * <ol>
 * <li>{@link ValueBean}�����������A���f���N���X(RDBMS�̃��R�[�h�ɂ�����)���쐬�B</li>
 * <li>Dao�N���X(���̃N���X)���p�������Ɩ�Dao�N���X���쐬</li>
 * <li>getBean(), getBeanList()�ɁA��L�ō쐬�������f���N���X�ASQL�A�p�����[�^�Ȃǂ�^���邱�Ƃ�SQL�����s����B</li>
 * </ol>
 * �ƂȂ�B
 * 
 * Datasource �̎擾���@�́A�R���X�g���N�^�ɓn���A{@link DatasourceFactory}�̎����N���X�ɈϏ�����B
 * 
 * @see DatasourceFactory
 * @author totec yagi
 */
public class Dao {
	/**
	 * SQL���t�@�C���̃f�t�H���g��
	 */
	public static final String SQL_FILE_NAME = "sql.properties";
	/**
	 * Dao�N���X�Ń��O���o�͂������ꍇ�Ɏg�p����B
     * <ul>
     * <li>���O�̐ݒ�́Alog4j.properties �t�@�C���ōs���B</li>
     * </ul>
	 */
	protected Logger log = LogUtil.getLogger(Dao.class);
	/**
	 * Datasource �����N���X
	 */
	private DatasourceFactory datasourceFactory;
	/**
	 * SQL����ێ�����
	 */
	private Map sqlMap = null;
	/**
	 * �R���X�g���N�^
	 */
	public Dao(DatasourceFactory datasourceFactory) {
		String sqlProp = getSqlPropertyPath();
		if (sqlProp != null) {
			loadSql(sqlProp);
		}
		this.setDatasourceFactory(datasourceFactory);
	}
	/**
	 * ���̃N���X���ێ����� Datasource �����N���X��Ԃ��B
	 * @return Datasource �����N���X
	 */
	public DatasourceFactory getDatasourceFactory() {
		return datasourceFactory;
	}

	/**
	 * ���̃N���X���ێ����� Datasource �����N���X��ݒ肷��B
	 * @param datasourceFactory Datasource �����N���X
	 */
	public void setDatasourceFactory(DatasourceFactory datasourceFactory) {
		this.datasourceFactory = datasourceFactory;
	}
	
	/**
     * SQL������`���ꂽ�v���p�e�B�t�@�C���̃p�X��Ԃ��B
	 * @return SQL������`���ꂽ�v���p�e�B�t�@�C���̃p�X
	 */
	public String getSqlPropertyPath() {
		return "/" + getClass().getPackage().getName().replace(".", "/") 
		      +"/" + SQL_FILE_NAME;
	}
	
	/**
     * DataSource���擾����B
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		return getDatasourceFactory().getDatasource();
	}

	/**
     * �w�肳�ꂽ�p�X�̃v���p�e�B�t�@�C����SQL��`�Ƃ��ēǂ݂��ށB
	 * @param path
	 */
	public void loadSql(String path) {
		try {
			sqlMap = QueryLoader.instance().load(path);
		} catch (IOException e) {
			throw new DatabaseAccessException(e);
		}
	}
	
	/**
     * �Ǎ��܂�Ă���SQL������L�[�Ɉ�v������̂��擾����B
	 * @param key SQL���̃L�[
	 * @return SQL��
	 */
	public String getSql(String key) {
		if (key == null || sqlMap == null) {
			return null;
		}
		return (String)sqlMap.get(key);
	}
	
	/**
     * �w�肳�ꂽSQL�������s���A���ʂ���Bean��List���쐬����B
	 * @param beanClazz SQL�����s���ʂ��i�[����Bean�N���X
	 * @param sql SQL��
	 * @return SQL�������s�������ʂ�Bean�Ƃ���List�Ɋi�[��������
	 */
	public List getBeanList(Class beanClazz, String sql) {
		return getBeanList(beanClazz, sql, null);
	}
    
	/**
     * �p�����[�^���w�肵�ASQL�������s���A���ʂ���Bean��List���쐬����B
     * SQL������ "?"���A�擪���珇�ɁA����params�ɒu����������B
     * @param beanClazz SQL�����s���ʂ��i�[����Bean�N���X
     * @param sql SQL��
	 * @param params SQL���ɍ������ރp�����[�^
     * @return SQL�������s�������ʂ�Bean�Ƃ���List�Ɋi�[��������
	 */
	public List getBeanList(Class beanClazz, String sql, Object[] params) {
		return getBeanList(null, beanClazz, sql, params);
	}

	/**
     * �p�����[�^���w�肵�ASQL�������s���A���ʂ���Bean��List���쐬����B
     * SQL������ "?"���A�擪���珇�ɁA����params�ɒu����������B
     * �g�����U�N�V�����𖾎��I�ɐ��䂵�����ꍇ�A�����R�~�b�g���[�h�𖳌��ɂ����AConnection�I�u�W�F�N�g�������Ƃ��ēn���B
     * �g�����U�N�V�����̏I���́AConnection�I�u�W�F�N�g�ɑ΂��āAcommit�Arollback���Ăяo�����Ƃōs���B
	 * @param conn Connection �g�����U�N�V�����Ǘ��p�ڑ�
     * @param beanClazz SQL�����s���ʂ��i�[����Bean�N���X
     * @param sql SQL��
	 * @param params SQL���ɍ������ރp�����[�^
     * @return SQL�������s�������ʂ�Bean�Ƃ���List�Ɋi�[��������
	 */
	public List getBeanList(Connection conn, Class beanClazz, String sql, Object[] params) {
		boolean isInTransaction = (conn != null);
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "beanlist:sql[" + sql + "],params:" +  Arrays.toString(params));
		}
		
		List result = null; 
		try {
			ResultSetHandler rsh = new BeanListHandler(beanClazz);
			QueryRunner runner = new QueryRunner();
			if (conn == null) {
				conn = getDataSource().getConnection();
			}
			if (params == null) {
				result = (List)runner.query(conn, sql, rsh);
			} else {
				result = (List)runner.query(conn, sql, params, rsh);
			}
		} catch (Exception e) {
			throw new DatabaseAccessException(e);
		} finally {
			if (!isInTransaction) {
				DbUtils.closeQuietly(conn);
			}
		}
		if (log.isDebugEnabled()) {
			if (result == null) {
				log.debug(getLogPrefix() + "beanlist:result[null]");
			} else {
				log.debug(getLogPrefix() + "beanlist:match[" + result.size() + "]");
			}
		}
		return result;
	}

	/**
     * �w�肳�ꂽSQL�������s���A���ʂ���Bean���쐬����B
     * @param beanClazz SQL�����s���ʂ��i�[����Bean�N���X
     * @param sql SQL��
	 * @return SQL�����s���ʂ��i�[����Bean
	 */
	public Object getBean(Class beanClazz, String sql) {
		return getBean(beanClazz, sql, null);
	}
    
	/**
     * �p�����[�^���w�肵�ASQL�������s���A���ʂ���Bean���쐬����B
     * SQL������ "?"���A�擪���珇�ɁA����params�ɒu����������B
     * @param beanClazz SQL�����s���ʂ��i�[����Bean�N���X
     * @param sql SQL��
     * @param params SQL���ɍ������ރp�����[�^
     * @return SQL�����s���ʂ��i�[����Bean
	 */
	public Object getBean(Class beanClazz, String sql, Object[] params) {
		return getBean(null, beanClazz, sql, params);
	}

	/**
     * �p�����[�^���w�肵�ASQL�������s���A���ʂ���Bean���쐬����B
     * SQL������ "?"���A�擪���珇�ɁA����params�ɒu����������B
     * �g�����U�N�V�����𖾎��I�ɐ��䂵�����ꍇ�A�����R�~�b�g���[�h�𖳌��ɂ����AConnection�I�u�W�F�N�g�������Ƃ��ēn���B
     * �g�����U�N�V�����̏I���́AConnection�I�u�W�F�N�g�ɑ΂��āAcommit�Arollback���Ăяo�����Ƃōs���B
	 * @param conn Connection �g�����U�N�V�����Ǘ��p�ڑ�
     * @param beanClazz SQL�����s���ʂ��i�[����Bean�N���X
     * @param sql SQL��
     * @param params SQL���ɍ������ރp�����[�^
     * @return SQL�����s���ʂ��i�[����Bean
	 */
	public Object getBean(Connection conn, Class beanClazz, String sql, Object[] params) {
		boolean isInTransaction = (conn != null);
		
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "bean:sql[" + sql + "],params:" +  Arrays.toString(params));
		}
		Object result = null; 
		try {
			ResultSetHandler rsh = new BeanHandler(beanClazz);
			QueryRunner runner = new QueryRunner();
			if (conn == null) {
				conn = getDataSource().getConnection();
			}
			if (params == null) {
				result = runner.query(conn, sql, rsh);
			} else {
				result = runner.query(conn, sql, params, rsh);
			}
			
		} catch (Exception e) {
			throw new DatabaseAccessException(e);
		} finally {
			if (!isInTransaction) {
				DbUtils.closeQuietly(conn);
			}
		}
		if (log.isDebugEnabled()) {
			if (result == null) {
				log.debug(getLogPrefix() + "bean:result[null]");
			} else {
				log.debug(getLogPrefix() + "bean:match[" + result.toString()+ "]");
			}
		}
		return result;
	}
	
	/**
     * �w�肳�ꂽ�p�����[�^�ŁADML SQL��(update,insert,delete)�����s����B
     * SQL������ "?"���A�擪���珇�ɁA����params�ɒu����������B
	 * @param sql SQL��
	 * @param params �p�����[�^
	 * @return �Ώی���
	 */
	public int update(String sql, Object[] params) {
        return update(null, sql, params);
    }

	/**
     * �w�肳�ꂽ�p�����[�^�ŁADML SQL��(update,insert,delete)�����s����B
     * SQL������ "?"���A�擪���珇�ɁA����params�ɒu����������B
     * �g�����U�N�V�����𖾎��I�ɐ��䂵�����ꍇ�A�����R�~�b�g���[�h�𖳌��ɂ����AConnection�I�u�W�F�N�g�������Ƃ��ēn���B
     * �g�����U�N�V�����̏I���́AConnection�I�u�W�F�N�g�ɑ΂��āAcommit�Arollback���Ăяo�����Ƃōs���B
	 * conn Connection �g�����U�N�V�����Ǘ��p�ڑ�
	 * @param sql SQL��
	 * @param params �p�����[�^
	 * @return �Ώی���
	 */
	public int update(Connection conn, String sql, Object[] params) throws DatabaseAccessException {
		boolean isInTransaction = (conn != null);
		
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "update:sql[" + sql + "],params:" +  Arrays.toString(params));
		}
		int ret = -1;
        try {
        	QueryRunner runner = new QueryRunner();
        	if (conn == null) {
        		conn = getDataSource().getConnection();
        	}
        	ret = runner.update(conn, sql, params);
        } catch (Exception e) {
            throw new DatabaseAccessException(e);
        } finally {
			if (!isInTransaction) {
				DbUtils.closeQuietly(conn);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug(getLogPrefix() + "update:result[" + ret + "]");
		}
        return ret;
    }
	
	/**
	 * �����R�~�b�g���[�h�𖳌��ɂ���Connection���擾����
	 * @return �����R�~�b�g���[�h�𖳌��ɂ���Connection
	 */
	public Connection beginTransaction() {
		return beginTransaction(null);
	}
	
	/**
	 * �����R�~�b�g���[�h�𖳌��ɂ���Connection���擾����
	 * Connection �̕������x���̕ύX�����݂�B
	 * ���� isolationLevel �Ƃ��āA�ȉ��̂����ꂩ�����b�v����Integer�I�u�W�F�N�g��n���B
	 * <br/>
	 * <ul>
	 * <li>java.sql.Connection.TRANSACTION_NONE</li>
	 * <li>java.sql.TRANSACTION_READ_COMMITTED</li>
	 * <li>java.sql.TRANSACTION_READ_UNCOMMITTED</li>
	 * <li>java.sql.TRANSACTION_REPEATABLE_READ</li>
	 * <li>java.sql.TRANSACTION_SERIALIZABLE</li>
	 * </ul>
	 * null �̏ꍇ�͐ݒ�ύX�����݂Ȃ��B
	 * @param isolationLevel Connection�̕������x���ɂ��Ă̒萔��Integer�Ń��b�v�������́B
	 * @return �����R�~�b�g���[�h�𖳌��ɂ���Connection
	 * @see Connection
	 */
	public Connection beginTransaction(Integer isolationLevel) {
		log.debug(getLogPrefix() + "begin tranzaction.");
		Connection conn = getConnection(false);
		
		// �������x���̐ݒ�����s����
		if (isolationLevel != null) {
			try {
				conn.setTransactionIsolation(isolationLevel.intValue());
			} catch (SQLException e) {
				throw new DatabaseAccessException(e);
			}
		}
		
		// �������x���̃��O�o��
		if (log.isDebugEnabled()) {
			try {
				int lvl = conn.getTransactionIsolation();
				String lbl = "";
				switch (lvl) {
					case Connection.TRANSACTION_NONE:			  lbl = "NONE";             break;             
					case Connection.TRANSACTION_READ_COMMITTED:   lbl = "READ COMMITTED";   break;
					case Connection.TRANSACTION_READ_UNCOMMITTED: lbl = "READ UNCOMMITTED"; break;
					case Connection.TRANSACTION_REPEATABLE_READ:  lbl = "REPEATABLE READ";  break;
					case Connection.TRANSACTION_SERIALIZABLE:	  lbl = "SERIALIZABLE";     break;
					default:
				}
				log.debug(getLogPrefix() + "isolation level is " + lbl);
			} catch (SQLException e) {
				log.debug(getLogPrefix() + "can't get isolation level.",e);
			}
		}
		return conn;
	}

	/**
     * ���O�o�͂̃v���t�B�b�N�X���w�肷��B
     * @return ���O�o�͂̃v���t�B�b�N�X
     */
    protected String getLogPrefix() {
        return "[" + this.getClass().getName() + "] ";
    }	

	/**
	 * DataSource����Connection���擾����
	 * @return Connection
	 */
	public Connection getConnection() {
		return getConnection(true);
	}
	
	/**
	 * DataSource����Connection���擾����
	 * @param autocommit �����R�~�b�g���[�h�̎w��
	 * @return Connection
	 */
	public Connection getConnection(boolean autocommit) {
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			changeAutoCommitMode(conn, autocommit);
		} catch (SQLException e) {
			throw new DatabaseAccessException(e);
		}
		return conn;
	}

	/**
	 * �g�����U�N�V�����̃R�~�b�g���s���B
	 * @param conn �Ώ�Connection
	 */
	public void commitTranzaction(Connection conn) {
		log.debug(getLogPrefix() + "cmmit tranzaction.");
		changeAutoCommitMode(conn, true);
		DbUtils.commitAndCloseQuietly(conn);
	}
	
	/**
	 * �g�����U�N�V�����̃��[���o�b�N���s���B
	 * @param conn �Ώ�Connection
	 */
	public void rollbackTranzaction(Connection conn) {
		log.debug(getLogPrefix() + "rollback tranzaction.");
		changeAutoCommitMode(conn, true);
		DbUtils.rollbackAndCloseQuietly(conn);
	}
	
	/**
	 * Connection �̎����R�~�b�g���[�h��ύX����
	 * @param conn �Ώ�Connection
	 * @param autocommit �����R�~�b�g���[�h
	 */
	private void changeAutoCommitMode(Connection conn, boolean autocommit) {
		try {
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			log.error(getLogPrefix() + "change autocommit mode.",e);
		}
	}
}
