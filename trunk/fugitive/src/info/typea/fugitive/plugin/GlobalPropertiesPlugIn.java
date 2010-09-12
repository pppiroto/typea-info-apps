package info.typea.fugitive.plugin;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * �A�v���P�[�V�������x���̃v���p�e�B��ݒ肷��B
 * �l�̎w��́Astruts-config.xml �ɂčs��
 * 
 * &lt;plug-in className="info.typea.fugitive.plugin.GlobalPropertiesPlugIn"&gt;
 *   &lt;set-property property="isDebug"    value="false"/&gt;
 *   &lt;set-property property="serverName" value="typea.info"/&gt;
 * &lt;/plug-in&gt;
 *
 * @author Administrator
 */
public class GlobalPropertiesPlugIn implements PlugIn {
	private String isDebug;
	private String serverName;
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
	 */
	public void init(ActionServlet servlet, ModuleConfig config) 
		throws ServletException {
		ServletContext context = servlet.getServletContext();
		
		context.setAttribute("isDebug",    this.isDebug);
		context.setAttribute("serverName", this.serverName);
	}
	/**
	 * @param isDebug
	 */
	public void setIsDebug(String isDebug) {
		this.isDebug = isDebug;
	}
	/**
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
