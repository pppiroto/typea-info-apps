package info.typea.fugitive.filter;

import info.typea.fugitive.auth.AuthInfo;
import info.typea.fugitive.auth.CheckAuth;
import info.typea.fugitive.util.CollectionUtil;
import info.typea.fugitive.util.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.mail.internet.MimeUtility;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Basic�F�؂��A�v���P�[�V�����Ǘ����邽�߂̃t�B���^
 * <br/>
 * WEB-INF/web.xml �ɂāA�ȉ���̂悤�Ƀt�B���^�[�̐ݒ�������Ȃ��B
 * <pre style="color:blue;">
 *   &lt;filter&gt;
 *    &lt;filter-name&gt;BasicAuthorizationFilter&lt;/filter-name&gt;
 *    &lt;filter-class&gt;fugitive.fw.filter.BasicAuthorizationFilter&lt;/filter-class&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;authorizeClassName&lt;/param-name&gt;
 *      &lt;param-value&gt;nwf.nz00.TamAuthorizedCheck&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;realmName&lt;/param-name&gt;
 *      &lt;param-value&gt;fugitive&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *    &lt;filter-name&gt;BasicAuthorizationFilter&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre>
 * @author totec yagi
 */
public class BasicAuthenticationFilter implements Filter {
	/**
	 * ��������
	 */
	private String realmName;
    
	/**
	 * �F�؃N���X��
	 */
	private String authorizeClassName;
	
	/* @see javax.servlet.Filter#init(javax.servlet.FilterConfig) */
	public void init(FilterConfig config) throws ServletException {
		this.realmName = config.getInitParameter("realmName");
		this.authorizeClassName = config.getInitParameter("authorizeClassName");
	}
    
	/* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		ByteArrayInputStream bin = null;
		BufferedReader br = null;
		try {
			HttpServletRequest httpReq = (HttpServletRequest)request;
			
			AuthInfo authInfo = new AuthInfo(false);
			
			String basicAuthData = httpReq.getHeader("authorization");
			if (!StringUtil.isBlank(basicAuthData)) {
				// Basic�F�؂���A���[�U��/�p�X���[�h���擾����
				String basicAuthBody = basicAuthData.substring(6); // ex 'Basic dG9tY2F0OnRvbWNhdA== ' 
				bin = new ByteArrayInputStream(basicAuthBody.getBytes()); 
				br = new BufferedReader(
						new InputStreamReader(MimeUtility.decode(bin,"base64")));

				StringBuilder buf = new StringBuilder();
				String line = null;
				while ((line = br.readLine())!=null) {
					buf.append(line);
				}
				String[] loginInfo = buf.toString().split(":");
				String username = CollectionUtil.safeArrayElement(loginInfo,0,"");
				String password = CollectionUtil.safeArrayElement(loginInfo,1,"");
	
				// �F�؃`�F�b�N�N���X���Ăяo��
				CheckAuth ac 
					= (CheckAuth)Class.forName(this.authorizeClassName).newInstance();
				
				authInfo = ac.isAuthorized(request, response, username, password);
				authInfo.setUserName(username);
				authInfo.setPassword(password);
			}

			// �F�؏������N�G�X�g�Ɋi�[
			httpReq.setAttribute(AuthInfo.AUTH_INFO_KEY, authInfo);

			if (!authInfo.isAuthorized()) {
				//�u���E�U�� UnAuthorized�G���[(401)��Ԃ�
				HttpServletResponse httpRes = (HttpServletResponse)response;
				httpRes.setHeader("WWW-Authenticate","Basic realm=" + this.realmName);
				httpRes.setContentType("text/html");
				httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401
			} else {
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (bin!=null) bin.close();
				if (br !=null) br.close();
			} catch(Exception e) {}
		}
	}

	/* @see javax.servlet.Filter#destroy() */
	public void destroy() {
		this.realmName = null;
		this.authorizeClassName = null;
	}
}
