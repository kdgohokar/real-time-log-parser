package com.me.logparser.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.me.logparser.service.LogParserService;
import com.me.logparser.tailer.CustomTailer;

import lombok.extern.slf4j.Slf4j;

/**
 * The class LogParserServiceImpl : Service class for LogParser
 * @author Kiran Gohokar
 *
 */
@Slf4j
@Service
public class LogParserServiceImpl implements LogParserService {

	@Override
	public Map<String, Integer> getHttpStatusCodeCount() {
		return null;
	}

	@Override
	public void parseLog(String fileName) {
		log.info("Starting the log parser for file : {}", fileName);
		
		CustomTailer tailer = new CustomTailer();
		tailer.read(fileName, true, 100);
		
		log.info("Log parser started.");
		
	}

}
