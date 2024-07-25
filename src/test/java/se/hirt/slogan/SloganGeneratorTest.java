package se.hirt.slogan;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SloganGeneratorTest {

	@Inject
	SloganGenerator sloganGenerator;

	@Test
	public void testGenerateSlogan() {
		String slogan = sloganGenerator.generateSlogan("JDK Mission Control");
		assertNotNull(slogan);
		System.out.println("The slogan was: " + slogan);
		assertTrue(slogan.contains("JDK Mission Control"));
	}
}
