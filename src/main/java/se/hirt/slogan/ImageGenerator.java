package se.hirt.slogan;

import jakarta.enterprise.context.ApplicationScoped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class ImageGenerator {
	public enum Background {
		CITY("city"),
		LUSH("lush"),
		NEBULA("nebula"),
		NIGHT("night"),
		OCEAN("ocean"),
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

	public byte[] generateImage(String slogan, String backgroundStr, String textColor, boolean dropShadow) throws IOException {
		Background background;
		try {
			background = "random".equalsIgnoreCase(backgroundStr)
					? Background.getRandom()
					: Background.valueOf(backgroundStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			slogan = "-->" + backgroundStr + " is not a valid background <--";
			background = Background.SUNSET;
		}
		try (InputStream is = getClass().getResourceAsStream("/backgrounds/" + background.getFileName() + ".png")) {
			if (is == null) {
				throw new IOException("Background image not found: " + background);
			}
			BufferedImage image = ImageIO.read(is);

			Graphics2D g2d = image.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setFont(new Font("Arial", Font.BOLD, 12));

			FontMetrics fm = g2d.getFontMetrics();
			int x = (image.getWidth() - fm.stringWidth(slogan)) / 2;
			int y = ((image.getHeight() - fm.getHeight()) / 2) + fm.getAscent();

			// Draw drop shadow, if wanted
			if (dropShadow) {
				g2d.setColor(Color.BLACK);
				g2d.drawString(slogan, x + 2, y + 2);
			}
			g2d.setColor(Color.decode(textColor));
			g2d.drawString(slogan, x, y);

			g2d.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			return baos.toByteArray();
		}
	}
}
