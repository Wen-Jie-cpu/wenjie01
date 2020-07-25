package com.jiyun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jiyun.bean.Dept;
import com.jiyun.bean.Stu;
import com.jiyun.bean.StuVo;

public interface StuMapper {

	List<Stu> findAll(StuVo stuVo);
//查询部门
	@Select("select * from dept")
	List<Dept> findDept();
	@Insert("insert into stu value(null,#{sname},#{age},#{sex},#{depid},#{birthday},#{pic})")
	int addStu(Stu stu);
	@Update("update stu set sname=#{sname},age=#{age},sex=#{sex},depid=#{depid},birthday=#{birthday},pic=#{pic} where sid=#{sid}")
	int updateStu(Stu stu);
	
	int delStu(@Param("ids")Integer[] ids);

}
