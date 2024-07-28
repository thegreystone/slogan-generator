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

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Path("/")
public class SloganResource {
	@Inject
	SloganGenerator sloganGenerator;

	@Inject
	ImageGenerator imageGenerator;

	@GET
	@Path("/text")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTextSlogan(@QueryParam("item") String item) {
		return sloganGenerator.generateSlogan(item);
	}

	@GET
	@Path("/image")
	@Produces("image/png")
	public Response getImageSlogan(
			@QueryParam("item") String item, @QueryParam("background") @DefaultValue("ocean") String background,
			@QueryParam("textColor") @DefaultValue("#FFFFFF") String textColor, @QueryParam("dropshadow") @DefaultValue("true") String dropShadow) throws IOException {
		String slogan = sloganGenerator.generateSlogan(item);
		byte[] imageBytes = imageGenerator.generateImage(slogan, background, textColor, Boolean.valueOf(dropShadow));
		return Response.ok(imageBytes).type("image/png").build();
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_HTML)
	public String testImages() {
		StringBuilder html = new StringBuilder("<html><body>");
		String[] items = {"JDK Mission Control", "Java", "OpenJDK"};
		for (String item : items) {
			for (ImageGenerator.Background bg : ImageGenerator.Background.values()) {
				String slogan = sloganGenerator.generateSlogan(item);
				String imgUrl = "/image?item=" + URLEncoder.encode(item,
						StandardCharsets.UTF_8) + "&background=" + bg.name().toLowerCase() + "&textColor=%23FFFFFF";
				html.append("<p>").append(slogan).append("</p>");
				html.append("<img src='").append(imgUrl).append("'><br><br>");
			}
		}
		html.append("</body></html>");
		return html.toString();
	}
}
