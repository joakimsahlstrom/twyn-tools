package se.jsa.twyn;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class TwynRestTemplateFactoryTest {

	@Test
	public void canCreateRestTemplate() throws Exception {
		assertNotNull(TwynRestTemplateFactory.create(Twyn.forTest()));
	}

}
