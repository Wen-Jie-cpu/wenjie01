package com.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiyun.bean.Stu;
import com.jiyun.mapper.StuMapper;

@Service
public class StuService {
@Autowired
private StuMapper stuMapper;

public List<Stu> findAll() {
	// TODO Auto-generated method stub
	return stuMapper.findAll();
}
}
