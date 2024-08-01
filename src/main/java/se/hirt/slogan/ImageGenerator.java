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
package se.hirt.slogan;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class ImageGenerator {
	public enum Background {
		BLUE("blue"),
		CITY("city"),
		CLOUDS("clouds"),
		LAKE("lake"),
		LAKE2("lake2"),
		LAKE3("lake3"),
		LAKENIGHT("lakenight"),
		LUSH("lush"),
		MOUNTAIN("mountain"),
		NEBULA("nebula"),
		NIGHT("night"),
		OCEAN("ocean"),
		PURPLE("purple"),
		SUNSET("sunset");

		private final String fileName;

		Background(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}

		public static Background getRandom() {
			return values()[ThreadLocalRandom.current().nextInt(values().length)];
		}
	}

	public byte[] generateImage(String slogan, ImageConfig config) throws IOException {
		boolean configIsValid = true;
		if (!config.isValid() || slogan.isBlank()) {
			if (slogan.isBlank()) {
				slogan =  "--> Must set either item or slogan parameter! <--";
			} else {
				slogan = "-->" + config.getValidationError() + "<--";
			}
			config = new ImageConfig.Builder().background("lakenight").fontSize(10).textColor("#FFFFFF").build();
		}

		try (InputStream is = getClass().getResourceAsStream("/backgrounds/" + config.getBackground().getFileName() + ".png")) {
			if (is == null) {
				throw new IOException("Background image not found: " + config.getBackground().getFileName());
			}
			byte[] backgroundBytes = is.readAllBytes();
			BufferedImage image = Imaging.getBufferedImage(backgroundBytes);

			Graphics2D g2d = image.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			//noinspection MagicConstant
			g2d.setFont(new Font("Arial", config.getFontStyle(), config.getFontSize()));

			FontMetrics fm = g2d.getFontMetrics();
			int x = (image.getWidth() - fm.stringWidth(slogan)) / 2;
			int y = ((image.getHeight() - fm.getHeight()) / 2) + fm.getAscent();

			// Draw drop shadow, if wanted
			if (config.isDropShadow()) {
				g2d.setColor(Color.BLACK);
				g2d.drawString(slogan, x + config.getDropShadowDistance(), y + config.getDropShadowDistance());
			}
			g2d.setColor(Color.decode(config.getTextColor()));
			g2d.drawString(slogan, x, y);

			g2d.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			Imaging.writeImage(image, baos, ImageFormats.PNG);
			return baos.toByteArray();
		}
	}
}
