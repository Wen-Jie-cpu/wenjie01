package com.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiyun.bean.Dept;
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

public List<Dept> findDept() {
	// TODO Auto-generated method stub
	return stuMapper.findDept();
}

public int addStu(Stu stu) {
	// TODO Auto-generated method stub
	return stuMapper.addStu(stu);
}

public int updateStu(Stu stu) {
	int i = stuMapper.updateStu(stu);
	return i;
}

public int delStu(Integer[] ids) {
	// TODO Auto-generated method stub
	return stuMapper.delStu(ids);
}
}
