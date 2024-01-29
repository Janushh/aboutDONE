package com.medical.api.controller;

import com.itextpdf.text.DocumentException;
import com.medical.api.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class QueryController {

    private final QueryService queryService;

    @PostMapping("/ask")
    public ResponseEntity<byte[]> askQuestion(@RequestParam String username, @RequestParam String question) throws DocumentException {
        return queryService.processAskQuestion(username, question);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<byte[]> generateReportForUser(@PathVariable String username) throws DocumentException {
        return queryService.processGenerateReportForUser(username);
    }
}
