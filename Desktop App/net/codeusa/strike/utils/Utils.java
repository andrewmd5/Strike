package net.codeusa.strike.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Utils {
	public static String readFile(final String path, final Charset encoding) {

		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (final IOException e) {
 
			return "null";
		}
		return new String(encoded, encoding);
	}
	
	public static void writeFile(final String content, final String path) {

		try {
			final PrintWriter writer = new PrintWriter(path);
			writer.print("");
			writer.close();
			Files.write(Paths.get(path), content.getBytes(),
					StandardOpenOption.CREATE);
		} catch (final IOException e) {

			e.printStackTrace();
		}
	}
}
