package com.me.logparser.api;

import static com.me.logparser.common.Constants.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.me.logparser.common.Constants;
import com.me.logparser.service.LogParserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kiran Gohokar
 *
 */
@Slf4j
@RestController("/api")
public class MainController {

	@Autowired
	private LogParserService service;

	@GetMapping("/parser/start")
	public ResponseEntity<String> startParser(@RequestParam(required=true) String fileName) {

		log.info("Got the request to start the parser for fileName : {} ", fileName);

		service.parseLog(fileName);
		
		return ResponseEntity.ok(SUCCESS);
	}

	
	@GetMapping("/")
	public String index() {
		return Constants.HELLO_MESSAGE;
	}

}
