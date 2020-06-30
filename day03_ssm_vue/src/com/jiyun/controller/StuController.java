package com.jiyun.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiyun.bean.Stu;
import com.jiyun.service.StuService;

@Controller
public class StuController {
@Autowired
private StuService stuService;

@RequestMapping("toShow")
public String toShow(){
	return "show";
}

	@RequestMapping("findAll")
	@ResponseBody
	public List<Stu> findAll(){
		List<Stu>list = stuService.findAll();
		System.out.println("list");
		return list;
	}
	
}
