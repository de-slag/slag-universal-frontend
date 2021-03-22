package de.slag.webgui.basic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasicWebTargetCallBuilderTest {

	BasicWebTargetCallBuilder builder;

	@BeforeEach
	void setUp() throws Exception {
		builder = new BasicWebTargetCallBuilder();
	}

	@Test
	void testNoTarget() {
		assertThrows(Exception.class, () -> builder.build());
	}
	
	@Test
	void testNoEndpoint() {
		builder.withTarget("http://localhost:18080");		
		assertThrows(Exception.class, () -> builder.build());
	}

}
