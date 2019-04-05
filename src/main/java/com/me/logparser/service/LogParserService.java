package com.me.logparser.service;

import java.util.Map;

/**
 * @author Kiran Gohokar
 *
 */
public interface LogParserService {
	
	/**
	 * @return the HTTP status code count from the logs in descending order
	 */
	Map<String, Integer> getHttpStatusCodeCount();
	
	/**
	 * Starts the real time streaming and parsing of logs from the input filename.
	 * @param fileName
	 */
	void parseLog(String fileName);
}
