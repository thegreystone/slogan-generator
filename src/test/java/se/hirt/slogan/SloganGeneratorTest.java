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

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class SloganGeneratorTest {

	@Inject
	SloganGenerator sloganGenerator;

	@Test
	public void testGenerateSlogan() {
		String slogan = sloganGenerator.generateSlogan("JDK Mission Control");
		assertNotNull(slogan);
		System.out.println("The slogan was: " + slogan);
		assertTrue(slogan.contains("JDK Mission Control"));
	}

	@Test
	public void testAcceptablePatternLengths() {
		int acceptableLength = 51 + "{item}".length();
		List<String> list = SloganGenerator.readDefaultPatterns();
		for (String pattern : list) {
			assertTrue(pattern.length() <= acceptableLength, "The pattern " + pattern + " is too long and should be trimmed or removed!");
		}
	}

	@Test
	public void testPatternsHaveItem() {
		List<String> list = SloganGenerator.readDefaultPatterns();
		int i = 0;
		for (String pattern : list) {
			assertTrue(pattern.contains("{item}"), "Pattern " + i + " (" + pattern + ") didn't contain {item}!");
			i++;
		}
	}
}
