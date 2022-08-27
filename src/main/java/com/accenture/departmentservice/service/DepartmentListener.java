package com.accenture.departmentservice.service;

import com.accenture.departmentservice.entity.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class DepartmentListener extends AbstractMongoEventListener<DepartmentEntity> {

    @Autowired
    private DepartmentService departmentServiceImpl;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<DepartmentEntity> event) {
        if(event.getSource().getDeptId() < 101){
            event.getSource().setDeptId(departmentServiceImpl.generateSequence(DepartmentEntity.SEQUENCE_NAME));
        }
    }
}
