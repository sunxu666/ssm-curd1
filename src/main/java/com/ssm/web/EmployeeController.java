package com.ssm.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.EmployeeMapper;
import com.ssm.entity.Employee;
import com.ssm.service.EmployeeService;
import com.ssm.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工
 *
 * @author
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/checkuser")
    @ResponseBody
    public Msg checkuser(@RequestParam String empName) {

        //先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|([\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须为6-16位英文和数字的组合或者2-5位中文");
        }

        boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 员工保存方法
     *
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid  Employee employee, BindingResult result) {
        if(result.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError:errors
                 ) {
                System.out.println("错误字段名"+fieldError.getField());
                System.out.println("错误字段内容"+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else{
            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }


    @RequestMapping(value = "/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        //引用分页插件,传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        List<Employee> emps = employeeService.getAll();
        //可以把显示的数据分页显示，并且还有连续的页码
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 查询所有员工的信息
     * 并且分页
     *
     * @return
     */
    //@RequestMapping(value = "/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //引用分页插件,传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        List<Employee> emps = employeeService.getAll();
        //可以把显示的数据分页显示，并且还有连续的页码
        PageInfo page = new PageInfo(emps, 5);

        model.addAttribute("pageInfo", page);
        return "list";
    }

    // @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute(employee);
        return "add";
    }

    //@RequestMapping(value = "/add1",method = RequestMethod.POST)
    public String addEmployee(Employee employee) {
        employeeService.add(employee);
        return "redirect:/emps?pn=99999";
    }


}















