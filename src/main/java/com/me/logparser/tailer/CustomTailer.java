package com.me.logparser.tailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The class CustomTailer : Custom implementation of Unix "tail -f" functionality.
 * @author Kiran Gohokar
 *
 */
public class CustomTailer {
	
	public void read(String fileName, boolean run, long refreshMillis) {
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			while(run) {
				String line = br.readLine();
				if (null == line) {
					Thread.sleep(refreshMillis);
				} else {
					System.out.println(line);
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
