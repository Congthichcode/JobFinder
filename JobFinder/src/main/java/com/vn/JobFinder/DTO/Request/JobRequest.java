package com.vn.JobFinder.DTO.Request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequest {
    public String title;
    public String company;
    public String location;
    public String description;
}
