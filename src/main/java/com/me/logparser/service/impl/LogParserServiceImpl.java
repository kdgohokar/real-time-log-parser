package com.me.logparser.service.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.me.logparser.common.Constants;
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
	
	private CustomTailer tailer = null; 
	
	@Value("${http.status.code.regex}")
	private String regex;
	

	@Override
	public Map<String, Integer> getHttpStatusCodeCount() {
		return CustomTailer.getHttpStatusCount();
	}

	@Override
	public void parseLog(String fileName) {
		
		log.info("Starting the log parser for file : {}", fileName);
		
		tailer = new CustomTailer(fileName, true, 10, regex);
		CompletableFuture.runAsync(tailer);
		
		log.info("Log parser started.");
		
	}

	@Override
	public String stopParser() {
		if (null != tailer && tailer.isRunning()) {
			log.info("Stopping the tailer...");
			tailer.stop();
			return Constants.STOP_SUCCESS_RESPONSE;
		} else {
			log.info("Tailer not running...");
			return Constants.STOP_FAILURE_RESPONSE;
		}
	}

}
