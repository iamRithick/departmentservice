package com.accenture.departmentservice.service;

import com.accenture.departmentservice.bean.DepartmentBean;
import com.accenture.departmentservice.dao.DepartmentDAO;
import com.accenture.departmentservice.entity.DepartmentEntity;
import com.accenture.departmentservice.entity.DepartmentSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    DepartmentDAO departmentDAO;
    MongoOperations mongoOperations;

    /**
     * Parameterized Constructor <br/>
     *
     * @param departmentDAO
     * @param mongoOperations
     */
    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO, MongoOperations mongoOperations) {
        this.departmentDAO = departmentDAO;
        this.mongoOperations = mongoOperations;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method fetches the list of all Departments entity,
     * convert them to bean and return the bean list <br/>
     *
     * @return List of Department beans
     * @throws Exception
     */
    @Override
    public List<DepartmentBean> getAllDepartments() throws Exception{
        log.info("Entering method to get all departments...");
        List<DepartmentBean> beans = null;
        try {
            List<DepartmentEntity> entities = departmentDAO.findAll();
            beans = new ArrayList<DepartmentBean>();
            for (DepartmentEntity entity : entities) {
                beans.add(convertEntityToBean(entity));
            }
            log.info("Exiting method.");
        }
        catch (Exception e){
            throw e;
        }
        return beans;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method fetches the Department entity based on id,
     * converts it into Department bean and returns it <br/>
     *
     * @param id
     * @return Department bean having the respective Department id
     * @throws Exception
     */
    @Override
    public DepartmentBean getDepartmentById(int id) throws Exception{
        DepartmentBean departmentBean = null;
        try {
            log.info("Entering method to get department by Id...");
            DepartmentEntity entity = departmentDAO.findByDeptId(id);
            departmentBean = convertEntityToBean(entity);
            log.info("Exiting method.");
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Department ID not present!");
        }
        catch (Exception e){
            throw e;
        }
        return departmentBean;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method inserts a Department entity to the Department collection <br/>
     *
     * @param departmentBean
     * @return The inserted Department bean
     * @throws DuplicateKeyException
     * @throws Exception
     */
    @Override
    public DepartmentBean addDepartment(DepartmentBean departmentBean) throws Exception{
        DepartmentBean bean = null;
        try {
            if(departmentBean.getDeptId()==0){
                departmentBean.setDeptId(generateSequence(DepartmentEntity.SEQUENCE_NAME));
            }
            DepartmentEntity departmentEntity = convertBeanToEntity(departmentBean);
            departmentDAO.save(departmentEntity);
            bean = convertEntityToBean(departmentEntity);
        }
        catch (DuplicateKeyException e){
            if(e.getMessage().contains("deptId")){
                throw new DuplicateKeyException("Department ID already exists!");
            }
            else{
                throw new DuplicateKeyException("Department Name already exists!");
            }
        }
        catch (Exception e){
            mongoOperations.findAndModify(new Query().addCriteria(Criteria.where("_id").is(DepartmentEntity.SEQUENCE_NAME)),
                    new Update().inc("seq",-1), DepartmentSequence.class);
            throw e;
        }
        return bean;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method gets the list of Department beans to
     * be inserted converts them into Department Entity and
     * inserts the new ones. Existing Departments are omitted.<br/>
     *
     * @param departmentBeanList
     * @return The list of inserted Department beans
     * @throws Exception
     */
    @Override
    public List<DepartmentBean> addDepartmentList(DepartmentBean[] departmentBeanList) throws Exception {
        List<DepartmentBean> addedDeptList = null;

        try{
            addedDeptList = new ArrayList<DepartmentBean>();
            for(DepartmentBean bean: departmentBeanList){
                try{
                    bean = addDepartment(bean);
                    addedDeptList.add(bean);
                }
                catch (Exception e){
                    continue;
                }
            }
        }
        catch (Exception e){
            throw e;
        }
        return addedDeptList;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method is used to generate the Department ID
     * using the department_sequence collection <br/>
     *
     * @param sequenceName
     * @return Department Id
     */
    @Override
    public int generateSequence(String sequenceName){
        DepartmentSequence counter = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(sequenceName));
            counter = mongoOperations.findOne(query, DepartmentSequence.class);
            if (counter == null) {
                mongoOperations.insert(new DepartmentSequence("department_sequence", 101));
            }
            counter = mongoOperations.findAndModify(query, new Update().inc("seq", 1), DepartmentSequence.class);

        }
        catch (Exception e){
            throw e;
        }
        return counter.getSeq();
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method is used to convert the Department Bean to Department Entity <br/>
     *
     * @param bean
     * @return Department Entity
     */
    public DepartmentEntity convertBeanToEntity(DepartmentBean bean){
        DepartmentEntity entity = new DepartmentEntity();
        BeanUtils.copyProperties(bean, entity);
        return entity;
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method is used to convert the Department Entity to Department Bean <br/>
     *
     * @param entity
     * @return Department Bean
     */
    public DepartmentBean convertEntityToBean(DepartmentEntity entity){
        DepartmentBean bean = new DepartmentBean();
        BeanUtils.copyProperties(entity, bean);
        return bean;
    }
}
