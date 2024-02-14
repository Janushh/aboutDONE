package com.medical.api.controller;

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
    public byte[] askQuestion(@RequestParam Integer userid, @RequestParam String question){
        return queryService.processAskQuestion(userid, question);
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<byte[]> generateReportForUser(@PathVariable Integer userid){
        return ResponseEntity.ok(queryService.processGenerateReportForUser(userid));
    }

    @GetMapping("/getall")
    public ResponseEntity<byte[]> getAllQueriesAsPdf() {
        return ResponseEntity.ok(queryService.getAllQueriesAsPdfResponse());
    }
}
