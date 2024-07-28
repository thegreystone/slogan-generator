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

import java.awt.*;

public class ImageConfig {
	private String background = "random";
	private String textColor = "#FFFFFF";
	private boolean dropShadow = true;
	private int dropShadowDistance;
	private int fontSize = 12;
	private String fontName = "Arial";
	private int fontStyle = Font.BOLD;
	private float opacity = 1.0f;

	public String getBackground() {
		return background;
	}

	public String getTextColor() {
		return textColor;
	}

	public boolean isDropShadow() {
		return dropShadow;
	}

	public int getDropShadowDistance() {
		return dropShadowDistance;
	}

	public int getFontSize() {
		return fontSize;
	}

	public String getFontName() {
		return fontName;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public float getOpacity() {
		return opacity;
	}

	public static class Builder {
		private ImageConfig config = new ImageConfig();

		public Builder background(String background) {
			config.background = background;
			return this;
		}

		public Builder textColor(String textColor) {
			// Ensure textColor starts with #, but don't require it from users (since %23)
			if (!textColor.startsWith("#")) {
				textColor = "#" + textColor;
			}
			config.textColor = textColor;
			return this;
		}

		public Builder dropShadow(boolean dropShadow) {
			config.dropShadow = dropShadow;
			return this;
		}

		public Builder fontSize(int fontSize) {
			config.fontSize = fontSize;
			return this;
		}

		public Builder fontName(String fontName) {
			config.fontName = fontName;
			return this;
		}

		public Builder fontStyle(String fontStyle) {
			fontStyle = fontStyle.toLowerCase();
			if (fontStyle.contains("plain")) {
				config.fontStyle = Font.PLAIN;
				return this;
			}

			if (fontStyle.contains("bold")) {
				config.fontStyle = config.fontStyle | Font.BOLD;
			}

			if (fontStyle.contains("italic")) {
				config.fontStyle = config.fontStyle | Font.ITALIC;
			}
			return this;
		}

		public Builder opacity(float opacity) {
			config.opacity = opacity;
			return this;
		}

		public Builder dropShadowDistance(int dropShadowDistance) {
			config.dropShadowDistance = dropShadowDistance;
			return this;
		}

		public ImageConfig build() {
			return config;
		}

	}
}
