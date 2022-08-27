package com.accenture.departmentservice.controller;

import com.accenture.departmentservice.bean.DepartmentBean;
import com.accenture.departmentservice.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/departmentService")
public class DepartmentController {

    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    private DepartmentService departmentServiceImpl;

    @Autowired
    public DepartmentController(DepartmentService departmentServiceImpl) {
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> displayMessage(){
        return new ResponseEntity<String>("Department Service is up and running!", HttpStatus.OK);
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method returns the list of all departments <br/>
     *
     * @return List of available departments
     * @throws Exception
     */
    @GetMapping(value = "/getAllDeptDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DepartmentBean>> getAllDepartments() throws Exception{
        List<DepartmentBean> deptList = departmentServiceImpl.getAllDepartments();
        return new ResponseEntity<List<DepartmentBean>>(deptList, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method fetches the department using Department ID <br/>
     *
     * @param id
     * @return The Department bean
     * @throws Exception
     */
    @GetMapping(value = "/getDeptById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentBean> getDeptById(@PathVariable("id") int id) throws Exception{
        log.info("In Department Service API.");
        DepartmentBean bean = departmentServiceImpl.getDepartmentById(id);
        return new ResponseEntity<DepartmentBean>(bean, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method inserts the department bean passed in the
     * request body and returns the inserted bean <br/>
     *
     * @param departmentBean
     * @return The inserted Department bean
     * @throws Exception
     */
    @PostMapping(value = "/addDept", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentBean> addDepartment(@RequestBody DepartmentBean departmentBean) throws Exception{
        DepartmentBean bean = departmentServiceImpl.addDepartment(departmentBean);
        return new ResponseEntity<DepartmentBean>(bean, HttpStatus.OK);
    }

    /**
     *METHOD DESCRIPTION: <br/>
     * This method inserts the list of departments passed in
     * the request body. Omits the existing departments and
     * returns the list of Department beans inserted. <br/>
     *
     * @param departmentBeans
     * @return The list of inserted Department beans
     * @throws Exception
     */
    @PostMapping(value = "/addDeptList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DepartmentBean>> addDepartmentList(@RequestBody DepartmentBean[] departmentBeans) throws Exception{
        List<DepartmentBean> deptBeanList = departmentServiceImpl.addDepartmentList(departmentBeans);
        return new ResponseEntity<>(deptBeanList, HttpStatus.OK);
    }

    /**
     * METHOD DESCRIPTION: <br/>
     * This method handles all the exceptions thrown in the
     * Department Service API <br/>
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleExceptions(HttpServletRequest request, Exception ex){
        log.error("Requested URL: " + request.getRequestURI());
        log.error("Thrown Exception: " + ex.toString());
        String message = "An Error occured!" + "\n" + "Exception Caused:" + "\n" + ex.getMessage();
        return message;
    }
}
