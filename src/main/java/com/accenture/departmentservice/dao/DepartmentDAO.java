package com.accenture.departmentservice.dao;

import com.accenture.departmentservice.entity.DepartmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DepartmentDAO extends MongoRepository<DepartmentEntity, String> {

    DepartmentEntity findByDeptId(int deptId);

}
