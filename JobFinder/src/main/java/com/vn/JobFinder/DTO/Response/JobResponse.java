package com.vn.JobFinder.DTO.Response;


import com.vn.JobFinder.Entity.JobEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResponse {
    private Integer id;
    private String title;
    private String company;
    private String location;
    private String description;

    // Chuyển từ Entity sang Response
    public static JobResponse fromEntity(JobEntity entity){
        JobResponse res = new JobResponse();
        res.setId(entity.getId());
        res.setTitle(entity.getTitle());
        res.setCompany(entity.getCompany());
        res.setLocation(entity.getLocation());
        res.setDescription(entity.getDescription());
        return res;
    }

}
