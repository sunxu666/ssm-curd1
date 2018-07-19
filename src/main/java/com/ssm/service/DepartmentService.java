package com.ssm.service;

import com.ssm.dao.DepartmentMapper;
import com.ssm.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepts() {
        List<Department> lists = departmentMapper.selectByExample(null);
        return lists;
    }
}
