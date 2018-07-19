package com.ssm.service;

import com.ssm.dao.EmployeeMapper;
import com.ssm.entity.Employee;
import com.ssm.entity.EmployeeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    public void add(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 保存员工信息
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);

    }

    /**
     * 查询库里有没有重复姓名
     * 0代表库里没有可以使用，
     * @param empName
     * @return
     */
    public boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample();
       EmployeeExample.Criteria criteria = example.createCriteria();
       criteria.andEmpNameEqualTo(empName);
       long count = employeeMapper.countByExample(example);
       return count == 0;
    }
}
