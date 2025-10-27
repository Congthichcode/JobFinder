package com.vn.JobFinder.Repository;

import com.vn.JobFinder.Entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<JobEntity,Integer>, JpaSpecificationExecutor<JobEntity> {
}
