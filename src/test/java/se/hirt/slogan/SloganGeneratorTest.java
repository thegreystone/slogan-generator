package se.hirt.slogan;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
