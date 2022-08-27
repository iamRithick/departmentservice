package com.accenture.departmentservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "department_sequence")
public class DepartmentSequence {

    @Id
    private String id;
    private int seq;

    public DepartmentSequence() {
    }

    public DepartmentSequence(String id, int seq) {
        this.id = id;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "DepartmentSequence{" +
                "id='" + id + '\'' +
                ", seq=" + seq +
                '}';
    }
}
