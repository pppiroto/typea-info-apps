package info.typea.sample.restservice.provider;

import info.typea.sample.restservice.entity.Cities;
import info.typea.sample.restservice.entity.City;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLEncoder;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.web.util.HtmlUtils;

/**
 * @author 
 * @see http://d.hatena.ne.jp/shin/20100921/p3
 */
@Provider
public class CitiesWriter implements MessageBodyWriter<Cities> {
	private JAXBContext jaxbContext;
	
	public long getSize(Cities cities, 
			Class<?> type, 
			Type genericType,  
			Annotation[] annotation, 
			MediaType mediaType) {
		return -1;
	}

	public boolean isWriteable(Class<?> type, 
			Type genericType, 
			Annotation[] annotation, 
			MediaType mediaType) {
		
		return type.equals(Cities.class);
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 * @see http://www.java2s.com/Code/Java/JDK-6/MarshalJavaobjecttoxmlandoutputtoconsole.htm
	 */
	public void writeTo(Cities cities, Class<?> type, 
			Type genericType, 
			Annotation[] annotation, 
			MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {
		
		try {
			httpHeaders.add("Content-Type", "application/xml");

			// IE キャッシュコントロール
			// http://support.microsoft.com/kb/234067/ja
			 
			httpHeaders.add("Cache-Control", "no-cache");
			httpHeaders.add("Pragma", "no-cache");
			httpHeaders.add("Expires", "-1");
			
			
			Writer writer = new OutputStreamWriter(entityStream, "UTF-8");

			Marshaller mshr = jaxbContext.createMarshaller();
			mshr.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			for (City city : cities.getCity()) {
				city.setCityName(HtmlUtils.htmlEscape(city.getCityName()));
				city.setCountry(HtmlUtils.htmlEscape(city.getCountry()));
				city.setLanguage(HtmlUtils.htmlEscape(city.getLanguage()));
				city.setAirport(HtmlUtils.htmlEscape(city.getAirport()));
			}
			
			mshr.marshal(cities, writer);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JAXBContext getJaxbContext() {
		return jaxbContext;
	}
	public void setJaxbContext(JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}
}
