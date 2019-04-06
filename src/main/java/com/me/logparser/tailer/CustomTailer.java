package com.me.logparser.tailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;


/**
 * The class CustomTailer : Custom implementation of Unix "tail -f" functionality.
 * @author Kiran Gohokar
 *
 */
@Slf4j
public class CustomTailer implements Runnable {

	private final String fileName;
	private volatile boolean run = true;
	private long refreshMillis = 100;
	private final String regex;

	private static Map<String, Integer> countMap = new HashMap<>();

	/**
	 * @param fileName : Name of the log file.
	 * @param run : Continuously read from the file until this value is true. 
	 * @param refreshMillis : delay in millis to sleep the thread. Default 100ms
	 * @param regex : Regular expression to parse the HTTP status code.
	 */
	public CustomTailer(String fileName, boolean run, long refreshMillis, String regex) {
		this.fileName = fileName;
		this.run = run;
		this.refreshMillis = refreshMillis;
		this.regex = regex;
	}

	/**
	 * Get the run
	 * @return
	 */
	public boolean isRunning() {
		return run;
	}
	
	/**
	 * Stop the log parser
	 */
	public void start() {
		this.run = true;
	}
	
	/**
	 * Stop the log parser
	 */
	public void stop() {
		this.run = false;
	}

	
	@Override
	public void run() {
		read();
	}

	
	/**
	 * Read the line from the file continuously
	 */
	private void read() {
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			while(run) {
				String line = br.readLine();
				if (null == line) {
					Thread.sleep(refreshMillis);
				} else {
					log.info("Parsed line : {}", line);
					countHttpStatusCode(line);
				}
			}
		} catch (IOException | InterruptedException e) {
			log.error("Exception occurred while reading from the file - {}", e.getCause());
		}
	}

	
	/**
	 * Count the HTTP status code from the line param using regex
	 * @param line
	 */
	private void countHttpStatusCode(String line) {

		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE); 
		final Matcher matcher = pattern.matcher(line);


		while (matcher.find()) { 

			String response = matcher.group(8); 

			if (countMap.containsKey(response)) { 
				countMap.put(response, countMap.get(response) + 1); 
			} else { 
				countMap.put(response, 1); 
			}

			sortCounterMap();
		}
	}

	
	/**
	 * Get the HTTP status code count from the map
	 * @return
	 */
	public static Map<String, Integer> getHttpStatusCount() {
		Map<String, Integer> result = sortCounterMap();
		log.info("HTTP statuc code count : {}", result);
		return result;
	}

	
	/**
	 * Sort the countMap
	 * @return the new counter map sorted in descending order
	 */
	private static Map<String, Integer> sortCounterMap() {
		Map<String, Integer> result = countMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		result.forEach((k, v) -> System.out.println("HTTP " + k +" -> " + v));
		return result;
	}
}
