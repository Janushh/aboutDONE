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
    public ResponseEntity<byte[]> askQuestion(@RequestParam Integer userid, @RequestParam String question) throws DocumentException {
        return queryService.processAskQuestion(userid, question);
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<byte[]> generateReportForUser(@PathVariable Integer userid) throws DocumentException {
        return queryService.processGenerateReportForUser(userid);
    }

    @GetMapping("/getall")
    public ResponseEntity<byte[]> getAllQueriesAsPdf() throws DocumentException {
        return queryService.getAllQueriesAsPdfResponse();
    }
}
