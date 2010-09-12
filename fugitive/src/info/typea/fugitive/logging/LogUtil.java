package info.typea.fugitive.logging;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
	static {
		try {
			Properties prop = new Properties();
			String url = "/" + LogUtil.class.getPackage().getName().replaceAll("\\.", "/") 
			             + "/log4j.properties";
			prop.load(LogUtil.class.getResourceAsStream(url));
			PropertyConfigurator.configure(prop);
		} catch (Exception re) {
			re.printStackTrace();
		}
	}
	private LogUtil() {}
	/**
	 * Log4JÉçÉKÅ[ÇéÊìæÇ∑ÇÈ
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class clazz) {
		return Logger.getLogger(clazz);
	}
}
