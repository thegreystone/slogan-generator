package com.slogangenerator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import se.hirt.slogan.ImageGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ImageGeneratorTest {

	@Inject
	ImageGenerator imageGenerator;

	@Test
	public void testGenerateImage() throws IOException {
		byte[] imageBytes = imageGenerator.generateImage("JDK Mission Control", "ocean", "#FFFFFF", true);
		assertNotNull(imageBytes);
		assertTrue(imageBytes.length > 0);

		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		assertEquals(460, image.getWidth());
		assertEquals(50, image.getHeight());
	}
}
