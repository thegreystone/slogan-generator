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
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Generates a test page.
 */
@Path("/test")
public class TestResource {
	@Inject
	SloganGenerator sloganGenerator;

	@Inject
	ImageGenerator imageGenerator;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String testImages() {
		StringBuilder html = new StringBuilder();
		html.append("<html><head><style>");
		html.append("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 20px; }");
		html.append("table { border-collapse: collapse; width: 100%; background-color: white; }");
		html.append("th, td { padding: 10px; border: 1px solid #ddd; text-align: left; }");
		html.append("th { background-color: #4CAF50; color: white; }");
		html.append(".slogan { background-color: #e6f3ff; }");
		html.append(".item { background-color: #fff0e6; }");
		html.append(".background { background-color: #e6ffe6; }");
		html.append("img { max-width: 100%; height: auto; display: block; margin: 10px 0; }");
		html.append("</style></head><body>");

		html.append("<table>");
		html.append("<tr><th>Slogan</th><th>Item</th><th>Background</th><th>Image</th></tr>");

		String[] items = {"JDK Mission Control", "Java"};
		for (String item : items) {
			for (ImageGenerator.Background bg : ImageGenerator.Background.values()) {
				String staticSlogan = sloganGenerator.generateSlogan(item);
				String imgUrl = "image?slogan=" + URLEncoder.encode(staticSlogan, UTF_8) + "&background=" + bg.name()
						.toLowerCase() + "&textColor=%23FFFFFF";

				html.append("<tr>");
				html.append("<td class='slogan'>").append(staticSlogan).append("</td>");
				html.append("<td class='item'>").append(item).append("</td>");
				html.append("<td class='background'>").append(bg.getFileName()).append("</td>");
				html.append("<td><img src='").append(imgUrl).append("' alt='Generated Slogan Image'></td>");
				html.append("</tr>");
			}
		}
		html.append("</table></body></html>");
		return html.toString();
	}
}
