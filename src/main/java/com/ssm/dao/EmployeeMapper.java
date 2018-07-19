package com.ssm.dao;

import com.ssm.entity.Employee;
import com.ssm.entity.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer empId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    /**
     * 根据条件查询所有成员和部门，条件为null
     * @param example
     * @return
     */
    List<Employee> selectByExampleWithDept(Employee example);
    /**
     * 根据empId查询所有成员和部门
     * @param empId
     * @return
     */
    Employee selectByPrimaryKeyWithDept(Integer empId);
}