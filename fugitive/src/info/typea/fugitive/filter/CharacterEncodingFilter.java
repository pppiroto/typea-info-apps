package info.typea.fugitive.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Web�A�v���P�[�V�����ł̕����R�[�h���ꊇ��������t�B���^
 * <br/>
 * Struts��ActionForm�ł́A���N�G�X�g�p�����[�^�̉�͎��ɁA�����G���R�[�f�B���O���s���Ȃ����߁A
 * �A�v���P�[�V�����S�̂Ŏg�p���镶���G���R�[�f�B���O���`�����t�B���^��K�p����B
 * <br/>
 * WEB-INF/web.xml �ɂāA�ȉ��̂悤�Ɏw�肷�邱�ƂŁA�L���ɂȂ�B
 * �����ŁA�g�p���镶���R�[�h��ݒ肷��B
 * <pre style="color:blue;">
 *   &lt;filter&gt;
 *    &lt;filter-name&gt;CharacterEncodingFilter&lt;/filter-name&gt;
 *    &lt;filter-class&gt;fugitive.fw.filter.CharacterEncodingFilter&lt;/filter-class&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;encording&lt;/param-name&gt;
 *      &lt;param-value&gt;UTF-8&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *    &lt;filter-name&gt;CharacterEncodingFilter&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre>
 * @author totec yagi
 */
public class CharacterEncodingFilter implements Filter {
	/**
	 * �����G���R�[�f�B���O
	 */
	private String encording = null;
	
	/* @see javax.servlet.Filter#init(javax.servlet.FilterConfig) */
	public void init(FilterConfig config) throws ServletException {
		this.encording = config.getInitParameter("encording");
	}

	/* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding(this.encording);
		filterChain.doFilter(request, response);
		response.setCharacterEncoding(this.encording);
	}

	/* @see javax.servlet.Filter#destroy() */
	public void destroy() {
		this.encording = null;
	}
}
