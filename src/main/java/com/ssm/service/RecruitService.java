package com.ssm.service;

import com.ssm.dao.RecruitMapper;
import com.ssm.entity.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitService {
    @Autowired
    private  RecruitMapper recruitMapper;

    /**
     * 保存对象
     * @param recruit
     */
    public void AddRecruit(Recruit recruit) {
        recruitMapper.insertSelective(recruit);
    }
}
