package se.hirt.slogan;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Generates Slogans.
 */
@ApplicationScoped
public class SloganGenerator {
	private final List<String> patterns;

	public SloganGenerator() {
		this.patterns = readDefaultPatterns();
	}

	public SloganGenerator(List<String> patterns) {
		this.patterns = patterns;
	}

	public String generateSlogan(String item) {
		String pattern = patterns.get(ThreadLocalRandom.current().nextInt(patterns.size()));
		return pattern.replace("{item}", item);
	}

	private static List<String> readDefaultPatterns() {
		try (InputStream inputStream = SloganGenerator.class.getResourceAsStream("/slogan-patterns.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			return reader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("Failed to load slogan patterns", e);
		}
	}
}
