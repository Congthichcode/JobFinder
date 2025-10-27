package com.vn.JobFinder.Controller;


import com.vn.JobFinder.DTO.Request.JobRequest;
import com.vn.JobFinder.DTO.Response.JobResponse;
import com.vn.JobFinder.Service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse>createJob(@RequestBody JobRequest request){
        return ResponseEntity.ok(jobService.createJob(request));
    }


    // 🧩 2. API cập nhật Job
    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Integer id, @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    // 🧩 3. API lấy tất cả Job
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // 🧩 4. API lấy chi tiết Job theo ID
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Integer id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    // 🧩 5. API lấy Job có phân trang
    @GetMapping("/paging")
    public ResponseEntity<Page<JobResponse>> getJobsWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(jobService.getJobsWithPaging(page, size));
    }

    // 🧩 6. API search động theo title, location, company
    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String company) {
        return ResponseEntity.ok(jobService.searchJobs(title, location, company));
    }



}
