package se.jsa.twyn;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TwynRestTemplateFactory {

	public static RestTemplate create(Twyn twyn) {
		return new RestTemplate(Arrays.<HttpMessageConverter<?>>asList(new TwynHttpMessageReader(twyn), new ObjectMapperMessageWriter(twyn.getObjectMapper())));
	}

	private static class ObjectMapperMessageWriter implements HttpMessageConverter<Object> {
		private final ObjectMapper objectMapper;

		public ObjectMapperMessageWriter(ObjectMapper objectMapper) {
			this.objectMapper = Objects.requireNonNull(objectMapper);
		}

		@Override
		public boolean canRead(Class<?> clazz, MediaType mediaType) {
			return false;
		}

		@Override
		public boolean canWrite(Class<?> clazz, MediaType mediaType) {
			return true;
		}

		@Override
		public List<MediaType> getSupportedMediaTypes() {
			return Arrays.asList(MediaType.APPLICATION_JSON);
		}

		@Override
		public Object read(Class<? extends Object> clazz,
				HttpInputMessage inputMessage) throws IOException,
				HttpMessageNotReadableException {
			throw new UnsupportedOperationException("Read operations not supported.");
		}

		@Override
		public void write(Object t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			objectMapper.writeValue(outputMessage.getBody(), t);
		}
	}

	private static class TwynHttpMessageReader implements HttpMessageConverter<Object> {
		private final Twyn twyn;

		public TwynHttpMessageReader(Twyn twyn) {
			this.twyn = Objects.requireNonNull(twyn);
		}
		@Override
		public boolean canRead(Class<?> clazz, MediaType mediaType) {
			return true;
		}

		@Override
		public boolean canWrite(Class<?> clazz, MediaType mediaType) {
			return false;
		}

		@Override
		public List<MediaType> getSupportedMediaTypes() {
			return Arrays.asList(MediaType.APPLICATION_JSON);
		}

		@Override
		public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
			return twyn.read(inputMessage.getBody(), clazz);
		}

		@Override
		public void write(Object t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			throw new UnsupportedOperationException("Write operations not supported!");
		}
	}

}
