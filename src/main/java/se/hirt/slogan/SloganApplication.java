package se.hirt.slogan;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class SloganApplication {
	public static void main(String... args) {
		Quarkus.run(args);
	}
}
