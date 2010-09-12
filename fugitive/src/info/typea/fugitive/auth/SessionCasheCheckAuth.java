package info.typea.fugitive.auth;

import info.typea.fugitive.filter.BasicAuthenticationFilter;
import info.typea.fugitive.logging.LogUtil;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * �F�،��ʂ��Z�b�V�����ɃL���b�V������B
 * <br/>
 * �F�،��ʂ��Z�b�V�����Ɋi�[���邱�Ƃɂ��A�F�؂ɃR�X�g��������ꍇ�ȂǓ���Z�b�V�����ł���ΔF�؍ς݂̏�Ԃ��ێ����邱�Ƃ��ł��邽��
 * �F�؂ɂ�����R�X�g(�Ⴆ�΃f�[�^�x�[�X�ɔF�؏�񂪊i�[����Ă���̂ł���΁A�擾�R�X�g)���y���ł���B
 * <br/>
 * �������A�Z�b�V�����ɏ����i�[���邽�߁A�L���b�V���������Ԃ́A�Z�b�V�����̗L�����ԂɈˑ�����B
 * WEB-INF/web.xml �̈ȉ��̃Z�N�V�����ŁA�Z�b�V�����̃^�C���A�E�g�̎��Ԃ�ݒ肷�邱�Ƃ��ł���B
 * �������ɂ���ɂ́A 0 ���w�肷��B
 * <pre style="color:blue">
 *  &lt;session-config&gt;
 *    &lt;session-timeout&gt;15&lt;/session-timeout&gt;
 *  &lt;/session-config&gt;
 * </pre>
 * �܂��A�L���b�V���̗L�����Ԃ́A�Z�b�V�����̗L�����Ԃ݂̂Ɉˑ�����̂ł͂Ȃ��A�Ǝ��ɗL�����Ԃ��Ǘ����Ă���B
 * setKeepCasheMinutes() ���\�b�h�𗘗p���A�F�؎�����̎���(��)�Ŏw�肷��B
 * <br/>
 * ����́A�Z�b�V�����̗L�����ԂƃL���b�V���̗L�����Ԃ𓯂��Ƃ����ꍇ�ɁA�����Ƃ̕s��v�������邱�Ƃ��z�肳��邽�߂̑΍�ł���B
 * �Ⴆ�΁A�F��OK�ŏ�񂪃L���b�V�����ꂽ��A���̔F�؏��(�Ⴆ�΃f�[�^�x�[�X)�ŃA�N�Z�X�s�ɐݒ肵���Ƃ��Ă��A
 * ���̏�񂪔��f�����̂ɂ́A�Z�b�V�����̃^�C���A�E�g��҂��˂΂Ȃ�Ȃ��B
 * �L���b�V���̗L�����Ԃ�K�x�ɒ������邱�Ƃɂ��A���̕s��v���N���鎞�Ԃ𒲐����邱�Ƃ��\�Ƃ���B
 * <br/>
 * {@link BasicAuthenticationFilter}���痘�p�����ꍇ�A�L���b�V���^�C���A�E�g~�ŔF�؂̏ꍇ�ł����āA
 * HTTP�w�b�_�ɖ��ߍ��܂�Ă���Basic�F�؏����A�ēx�F�؏���������̂ł���A���[�U���߂ł���B
 * �u���E�U���ŁA�ēx�F�؃_�C�A���O���\�������킯�ł͂Ȃ��B
 * 
 * @author totec yagi
 */
public abstract class SessionCasheCheckAuth implements CheckAuth {
	/**
	 * �F�؉�
	 */
	private AuthInfo authInfo;
	/**
	 * �F�؏������s����
	 */
	private long authrizedTime;
	/**
	 * �F�؃L���b�V������(��)
	 */
	private long keepCasheMinutes = 15;

	
	/**
	 * �F�؃N���X�Ń��O���o�͂������ꍇ�Ɏg�p����B
     * <ul>
     * <li>���O�̐ݒ�́Alog4j.properties �t�@�C���ōs���B</li>
     * </ul>
	 */
	protected Logger log = LogUtil.getLogger(SessionCasheCheckAuth.class);	

	/**
	 * �F�،��ʂ��i�[����L�[
	 */
	private final String SESSION_KEY = SessionCasheCheckAuth.class.getName();
	
	/**
	 * �T�u�N���X�Ŏ������A�F�؏������s��
	 * @param username ���[�U�[��
	 * @param password �p�X���[�h
	 * @return �F�؏���ێ�����AuthInfo�N���X
	 */
	public abstract AuthInfo authorize(String username, String password);

	/**
	 * �F�،��ʂ��Z�b�V�����Ɋi�[���邽�߂̃L�[
	 * �Ǝ��̃L�[�𗘗p�������ꍇ�A�T�u�N���X�ŃI�[�o�[���C�h����
	 * @return �F�،��ʂ��Z�b�V�����Ɋi�[���邽�߂̃L�[
	 */
	public String getSessionKey() {
		return SESSION_KEY;
	}
	
	/* (non-Javadoc)
	 * @see fugitive.fw.auth.AuthorizedCheck#isAuthorized(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.String, java.lang.String)
	 */
	public AuthInfo isAuthorized(ServletRequest request, ServletResponse response, String userName, String password) {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		AuthInfo authInfo = new AuthInfo();
		
		try {
			long now = System.currentTimeMillis();
			HttpSession session = httpReq.getSession();
			SessionCasheCheckAuth val = (SessionCasheCheckAuth)session.getAttribute(getSessionKey());
			if (val != null) {
				authInfo = val.getAuthInfo();
			}
			
			// �ȉ��̏ꍇ�A�F�؂��s��
			if (val == null                                       // �F�؃L���b�V���N���X���Ȃ��ꍇ
			    || !authInfo.isAuthorized()                       // �F�؂����s���Ă���ꍇ
				|| !authInfo.isMatchIdentity(userName, password)  // ���[�U���A�p�X���[�h����v���Ă��Ȃ��ꍇ
			    ||  val.isExpiredCache(now)                       // �F�؃L���b�V�����������Ă���ꍇ
				) {
				
				// �F�؂��s��
				authInfo = authorize(userName, password);
				
				// �F�؏���o�^
				this.setAuthInfo(authInfo);
				this.setAuthrizedTime(now);
				
				// �F�؏����L���b�V��
				session.setAttribute(getSessionKey(), this);
				
				log.info("authorization check." + logInfo(authInfo,now,httpReq));
			} else {
				log.debug("authorization check by cashe." + logInfo(authInfo,val.getAuthrizedTime(), httpReq));
			}
		} catch (Exception e) {
			// �F�؃L���b�V���ŗ�O���������Ă��A�V�X�e���G���[�Ƃ͂����ɁA�F�؏������s��
			log.error("authorization cashe doesn't work. exception occurred.", e);
			authInfo = authorize(userName, password);
			log.info("authorization check. without cashe." + logInfo(authInfo, System.currentTimeMillis(), httpReq));
		}
		return authInfo;
	}
	
	/**
	 * ���O�o�͎��̕t����񏑎�����
	 * @param result �F�،���
	 * @param timeMillis �F�؎���
	 * @param user �F�؃��[�U
	 * @param httpReq HttpServletRequest�̎Q��
	 * @return ���O������
	 */
	private String logInfo(AuthInfo info, long timeMillis, HttpServletRequest httpReq) {
		return  "[" + ((info == null)?"nothing auth info":info.toString()) 
		     + ", time:" + new Date(timeMillis) 
		     + ", request:" + httpReq.getRequestURI()
		     + "]";
	}
	
    /**
     * �L���b�V�����L�����ԓ����ۂ� 
     * @param now ���ݎ�
     * @return �L���Ȃ�true
     */
    public boolean isExpiredCache(long now) {
		return ((now - this.getAuthrizedTime()) >= this.getKeepCasheMilliSeconds());
	}

	/**
	 * �F�؏󋵂��擾
	 * @return �F�؍ς݂Ȃ�true
	 */
	public boolean isAuthorized() {
		return getAuthInfo().isAuthorized();
	}

	/**
	 * �F�؏󋵂�ݒ肷��
	 * @param authInfo �F�؍ς݂Ȃ�true 
	 */
	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}
	
	/**
	 * �F�؏󋵂�ݒ肷��
	 */
	public AuthInfo getAuthInfo() {
		if (this.authInfo == null) {
			this.authInfo = new AuthInfo();
		}
		return this.authInfo;
	}
	
	/**
	 * �F�؏������Ԃ��擾����
	 * @return �F�؏�������
	 */
	public long getAuthrizedTime() {
		return authrizedTime;
	}

	/**
	 * �F�؏������Ԃ�ݒ肷��
	 * @param authrizedTime �F�؏������Ԃ��擾����
	 */
	public void setAuthrizedTime(long authrizedTime) {
		this.authrizedTime = authrizedTime;
	}

	/**
	 * �L���b�V���ێ����Ԃ��擾����
	 * @return �L���b�V���ێ�����(��)
	 */
	public long getKeepCasheMinutes() {
		return keepCasheMinutes;
	}
	
	/**
	 * �L���b�V���ێ����Ԃ��擾����
	 * @return �L���b�V���ێ�����(��)
	 */
	public long getKeepCasheMilliSeconds() {
		return getKeepCasheMinutes() * 60000;
	}
	
	/**
	 * �L���b�V���ێ����Ԃ�ݒ肷��
	 * @param keepCasheMinutes �L���b�V���ێ�����(��)
	 */
	public void setKeepCasheMinutes(long keepCasheMinutes) {
		this.keepCasheMinutes = keepCasheMinutes;
	}
}
