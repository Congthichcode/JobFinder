package com.vn.JobFinder.Service;


import com.vn.JobFinder.DTO.Request.JobRequest;
import com.vn.JobFinder.DTO.Response.JobResponse;
import com.vn.JobFinder.Entity.JobEntity;
import com.vn.JobFinder.Repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // 🧩 1. Tạo mới Job
    public JobResponse createJob(JobRequest request){
        JobEntity entity=new JobEntity();
        entity.setTitle(request.getTitle());
        entity.setCompany(request.getCompany());
        entity.setLocation(request.getLocation());
        entity.setDescription(request.getDescription());
        return JobResponse.fromEntity(jobRepository.save(entity));
    }

    // 🧩 2. Cập nhật Job theo ID
    public JobResponse updateJob(Integer id, JobRequest request) {
        JobEntity existing = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Job có ID: " + id));

        existing.setTitle(request.getTitle());
        existing.setCompany(request.getCompany());
        existing.setLocation(request.getLocation());
        existing.setDescription(request.getDescription());

        return JobResponse.fromEntity(jobRepository.save(existing));
    }

    // 🧩 3. Lấy toàn bộ Job (không phân trang)
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(JobResponse::fromEntity)
                .toList();
    }

    // 🧩 4. Lấy chi tiết Job theo ID
    public JobResponse getJobById(Integer id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Job có ID: " + id));
        return JobResponse.fromEntity(job);
    }

    // 🧩 5. Lấy danh sách Job có phân trang
    public Page<JobResponse> getJobsWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<JobEntity> pageResult = jobRepository.findAll(pageable);

        // Chuyển từng JobEntity → JobResponse
        return pageResult.map(JobResponse::fromEntity);
    }

    // 🧩 6. Search động (theo title, location, company)
    public List<JobResponse> searchJobs(String title, String location, String company) {
        Specification<JobEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Nếu có truyền title thì thêm điều kiện LIKE
            if (title != null && !title.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));

            // Nếu có truyền location thì thêm điều kiện LIKE
            if (location != null && !location.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));

            // Nếu có truyền company thì thêm điều kiện LIKE
            if (company != null && !company.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%"));

            // Gộp tất cả điều kiện bằng AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return jobRepository.findAll(spec)
                .stream().map(JobResponse::fromEntity).toList();
    }
}
