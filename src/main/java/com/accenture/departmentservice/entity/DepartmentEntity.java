package com.accenture.departmentservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Department")
public class DepartmentEntity {

    @Transient
    public static final String SEQUENCE_NAME = "department_sequence";

    @Id
    private String objId;
    @Indexed(name = "deptId", background = true, unique = true, direction = IndexDirection.ASCENDING)
    private int deptId;
    @Indexed(unique = true)
    private String deptName;

    public DepartmentEntity() {
    }

    public DepartmentEntity(int deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
