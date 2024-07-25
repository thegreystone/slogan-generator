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
