package com.jiyun.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiyun.bean.Dept;
import com.jiyun.bean.Stu;
import com.jiyun.bean.StuVo;
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
	public PageInfo<Stu> findAll(@RequestBody StuVo stuVo){
		PageHelper.startPage(stuVo.getPageNum(), 3);
		
		List<Stu>list = stuService.findAll(stuVo);
		PageInfo<Stu>pageInfo = new PageInfo<Stu>(list);
		System.out.println("list");
		return pageInfo;
	}
//跳转添加页面并查询班级
	@RequestMapping("toAdd")
	public String toAdd(){
		return "add";
	}
//添加：
	@RequestMapping("addStu")
	@ResponseBody
	public int  addStu(@RequestBody Stu stu){
		int i = stuService.addStu(stu);
		
		return i;
		
	}
	//查询部门：
@RequestMapping("findDept")
@ResponseBody
public List<Dept> findDept(){
	List<Dept> dList = stuService.findDept();
	return dList;

}
//修改
@RequestMapping("updateStu")
@ResponseBody
public int updateStu(@RequestBody Stu stu){
	int i = stuService.updateStu(stu);
	return i;
}
//删除
@RequestMapping("delStu")
@ResponseBody
public int delStu(@RequestBody Integer[] ids){
	int i = stuService.delStu(ids);
	return i;
	
}

}
