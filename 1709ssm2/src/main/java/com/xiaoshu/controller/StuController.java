package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFChildAnchor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Clazz;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("stu")
public class StuController extends LogController{
	static Logger logger = Logger.getLogger(StuController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	
	@Autowired
	private StudentService ss;
	@RequestMapping("stuIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Clazz> cList = ss.findAllClazz();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("cList", cList);
		return "stu";
	}
	
	
	/**
	 * 备份
	 */
	@RequestMapping("outStu")
	public void outStu(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
		    String excelName = "手动备份"+time;
			List<Student> list = ss.findAll();
			String[] handers = {"学生编号","学生名","班级","性别","照片","生日","爱好"};
			// 1导入硬盘
			ExportExcelToDisk(request,handers,list, excelName);
			// 2导出的位置放入attachment表
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("", "对不起，备份失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 导出到硬盘
	@SuppressWarnings("resource")
	private void ExportExcelToDisk(HttpServletRequest request,
			String[] handers, List<Student> list, String excleName) throws Exception {
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			rowFirst.setHeight((short) 500);
			for (int i = 0; i < handers.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
			}
			//写标题了
			for (int i = 0; i < handers.length; i++) {
			    //获取第一行的每一个单元格
			    HSSFCell cell = rowFirst.createCell(i);
			    //往单元格里面写入值
			    cell.setCellValue(handers[i]);
			}
			int j=0;
			for (int i = 0;i < list.size(); i++) {
			    //获取list里面存在是数据集对象
			    Student stu = list.get(i);
			    String cname = stu.getClazz().getCname();
			    String hobby = stu.getHobby();
			    if ("h1909A".equals(cname) && hobby.contains("篮球")) {
			    	   //创建数据行
				    HSSFRow row = sheet.createRow(j+1);
				    //设置对应单元格的值
				    row.setHeight((short)400);   // 设置每行的高度
				    //"序号","操作人","IP地址","操作时间","操作模块","操作类型","详情"
				    row.createCell(0).setCellValue(j+1);
				    row.createCell(1).setCellValue(stu.getSname());
				    row.createCell(2).setCellValue(stu.getClazz().getCname());
				    row.createCell(3).setCellValue(stu.getSex());
				    row.createCell(4).setCellValue(stu.getImg());
				    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				    row.createCell(5).setCellValue(simpleDateFormat.format(stu.getBirthday()));
				    row.createCell(6).setCellValue(stu.getHobby());
				    
				    j++;
				}
			    
			    
			    
			 
			}
			//写出文件（path为文件路径含文件名）
				OutputStream os;
				File file = new File("D:/"+excleName+".xls");
				
				if (!file.exists()){//若此目录不存在，则创建之  
					file.createNewFile();  
					logger.debug("创建文件夹路径为："+ file.getPath());  
	            } 
				os = new FileOutputStream(file);
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}

	// 新增或修改
		@RequestMapping("inStu")
		public void inStu(MultipartFile excel,HttpServletRequest request,Student stu,HttpServletResponse response) throws Exception{
			JSONObject result=new JSONObject();
		
			try {
				InputStream inputStream = excel.getInputStream();
				HSSFWorkbook wb = new HSSFWorkbook(inputStream);
				HSSFSheet sheet = wb.getSheetAt(0);
				int lastRowNum = sheet.getLastRowNum();
				
			} catch (Exception e) {
				result.put("success", true);
			}
			WriterUtil.write(response, result.toString());
		}
		
		
	
	@RequestMapping(value="stuList",method=RequestMethod.POST)
	public void userList(Student stu,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
		/*	User user = new User();
			String username = request.getParameter("username");
			String roleid = request.getParameter("roleid");
			String usertype = request.getParameter("usertype");*/
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			/*if (StringUtil.isNotEmpty(username)) {
				user.setUsername(username);
			}
			if (StringUtil.isNotEmpty(roleid) && !"0".equals(roleid)) {
				user.setRoleid(Integer.parseInt(roleid));
			}
			if (StringUtil.isNotEmpty(usertype)) {
				user.setUsertype(usertype.getBytes()[0]);
			}*/
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Student> stuList= ss.findStuPage(stu,pageNum,pageSize,ordername,order);
			
			/*request.setAttribute("username", username);
			request.setAttribute("roleid", roleid);
			request.setAttribute("usertype", usertype);*/
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",stuList.getTotal() );
			jsonObj.put("rows", stuList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveStu")
	public void reserveUser(HttpServletRequest request,Student stu,String[] hobbys,HttpServletResponse response){
		Integer sId = stu.getSid();
		JSONObject result=new JSONObject();
		String hobby = "";
		hobby = StringUtils.join(hobbys,",");
		stu.setHobby(hobby);
		
		try {
			if (sId != null) {   // userId不为空 说明是修改
				Student student = ss.findByName(stu.getSname());
				if(student != null && student.getSid().compareTo(sId)==0||student==null){
					ss.updateStu(stu);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(ss.findByName(stu.getSname())==null){  // 没有重复可以添加
					ss.addStu(stu);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteStu")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				ss.deleteStu(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
}
