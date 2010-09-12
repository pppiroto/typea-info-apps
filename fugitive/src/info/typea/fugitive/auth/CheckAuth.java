package info.typea.fugitive.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ���[�U�[�F�ؗp�C���^�[�t�F�[�X
 * <br/>
 * �T�[�u���b�g�R���e�i�̋@�\�𗘗p�����ABasicAuthorizationFilter�N���X�𗘗p����Basic�F�؂��s���ꍇ�A
 * ���̃C���^�[�t�F�[�X�����������N���X���ȉ��̂悤��web.xml�Ŏw�肵�āA���[�U�F�؂��s���B
 * �ȉ��̗�ł́ACheckAuthImpl �������N���X�B
 * <br/>
 * WEB-INF/web.xml �ɂāA�ȉ���̂悤�Ƀt�B���^�[�̐ݒ�������Ȃ��B
 * <pre style="color:blue;">
 *   &lt;filter&gt;
 *     &lt;filter-name&gt/BasicAuthorizationFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt/fugitive.fw.filter.BasicAuthorizationFilter&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt/authorizeClassName&lt;/param-name&gt;
 *       &lt;param-value&gt/nwf.nz00.CheckAuthImpl&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt/realmName&lt;/param-name&gt;
 *       &lt;param-value&gt/fugitive&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt/BasicAuthorizationFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre>
 * 
 * @see info.typea.fugitive.filter.BasicAuthenticationFilter
 * @author totec yagi
 */
public interface CheckAuth {
	/**
     * ���[�U�F�؂��s��
     * @param request HttpServletRequest
     * @param response HttpServletResponse
	 * @param username ���[�UID
	 * @param password �p�X���[�h
	 * @return �F�؏���ێ�����AuthInfo�N���X
	 */
	AuthInfo isAuthorized(ServletRequest request, ServletResponse response, String username, String password);

	/**
	 * �F�؏󋵂�ݒ肷��
	 * @param authInfo �F�؍ς݂Ȃ�true 
	 */
	public void setAuthInfo(AuthInfo authInfo);
	
	/**
	 * �F�؏󋵂�ݒ肷��
	 */
	public AuthInfo getAuthInfo();
}
