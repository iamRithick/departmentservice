package com.accenture.departmentservice.service;

import com.accenture.departmentservice.bean.DepartmentBean;

import java.util.List;

public interface DepartmentService {
    List<DepartmentBean> getAllDepartments() throws Exception;

    DepartmentBean getDepartmentById(int id) throws Exception;

    DepartmentBean addDepartment(DepartmentBean departmentBean) throws Exception;

    List<DepartmentBean> addDepartmentList(DepartmentBean[] departmentBeanList) throws Exception;

    public int generateSequence(String sequenceName);
}
