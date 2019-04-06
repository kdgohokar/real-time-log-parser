package com.me.logparser.api;

import static com.me.logparser.common.Constants.PARSER_STARTED_RESPONSE;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.me.logparser.common.Constants;
import com.me.logparser.service.LogParserService;

import lombok.extern.slf4j.Slf4j;

/**
 * The MainController class: Handles the requests for log parser
 * @author Kiran Gohokar
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/parser")
public class MainController {

	@Autowired
	private LogParserService service;

	@GetMapping("/start")
	public ResponseEntity<String> startParser(@RequestParam(required=true) String fileName) {

		log.info("Got the request to start the parser for fileName : {} ", fileName);

		CompletableFuture.runAsync(() -> service.parseLog(fileName));

		return ResponseEntity.ok(PARSER_STARTED_RESPONSE);
	}

	@GetMapping("/stop")
	public ResponseEntity<String> stopParser() {
		log.info("Got the request to stop the parser");
		return ResponseEntity.ok(service.stopParser());
	}
	
	@GetMapping("/count")
	public ResponseEntity<Map<String, Integer>> getCount() {

		log.info("Got the request to check HTTP status code count");

		//JSON Object does not maintain the order. So the output could be unordered count
		return ResponseEntity.ok(service.getHttpStatusCodeCount());
	}

	@GetMapping("/")
	public String index() {
		return Constants.HELLO_MESSAGE;
	}

}
