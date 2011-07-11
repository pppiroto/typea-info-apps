package info.typea.sample.restservice.provider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import info.typea.sample.restservice.entity.City;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author piroto
 * @see http://docs.huihoo.com/apache/cxf/2.2.4/jax-rs.html#JAX-RS-MessageBodyProviders	
 * @deprecated
 */
@Consumes("application/xml")
@Provider
public class CityReader implements MessageBodyReader<City> {
	private JAXBContext jaxbContext;

	public boolean isReadable(Class<?> type, Type argenericType, Annotation[] annotations, MediaType mediaType) {
		
		return InputStream.class.isAssignableFrom(type);
	}

	public City readFrom(Class<City> type, Type argenericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> multVluedMap,
			InputStream in) throws IOException, WebApplicationException {
	
		try {
			Unmarshaller unmshr = jaxbContext.createUnmarshaller();
			return (City)unmshr.unmarshal(in);
	
		} catch (JAXBException e) {
			throw new WebApplicationException(e);
		}
	}

	public JAXBContext getJaxbContext() {
		return jaxbContext;
	}
	public void setJaxbContext(JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

}
