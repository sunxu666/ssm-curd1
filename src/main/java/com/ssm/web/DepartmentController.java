package com.ssm.web;

import com.ssm.dao.DepartmentMapper;
import com.ssm.entity.Department;
import com.ssm.service.DepartmentService;
import com.ssm.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 部门信息的控制层
 * @author
 */
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/depts")
    @ResponseBody
    public Msg getDepts(){
        List<Department> lists = departmentService.getDepts();

        return Msg.success().add("depts",lists);
    }
}
