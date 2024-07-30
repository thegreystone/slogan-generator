/*
 * Copyright (C) 2024 Marcus Hirt
 *                    www.hirt.se
 *
 * This software is free:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESSED OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright (C) Marcus Hirt, 2024
 */
package com.slogangenerator;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import se.hirt.slogan.ImageConfig;
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
		ImageConfig config = new ImageConfig.Builder().background("ocean").textColor("#FFFFFF").dropShadow(true).build();
		byte[] imageBytes = imageGenerator.generateImage("JDK Mission Control", config);
		assertNotNull(imageBytes);
		assertTrue(imageBytes.length > 0);

		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		assertEquals(460, image.getWidth());
		assertEquals(50, image.getHeight());
	}

	@Test
	public void testBadColorConfig() {
		ImageConfig config = new ImageConfig.Builder().build();
		assertTrue(config.isValid(), "Empty config should be all defaults and fine!");
		config = new ImageConfig.Builder().background("ocean").textColor("#FFFFF").dropShadow(true).build();
		assertFalse(config.isValid(), "Has invalid color (should be 6 characters after #)");
		config = new ImageConfig.Builder().background("ocean").textColor("FFFFFF").dropShadow(true).build();
		assertTrue(config.isValid(), "We allow people to forget the pound sign");
		config = new ImageConfig.Builder().fontSize(-20).build();
		assertFalse(config.isValid(), "We don't allow negative font sizes");
		config = new ImageConfig.Builder().dropShadowDistance(400).build();
		assertFalse(config.isValid(), "Unreasonable drop shadw distance!");
	}
}
