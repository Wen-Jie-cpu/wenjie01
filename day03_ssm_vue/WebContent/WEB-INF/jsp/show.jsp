<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/axios-0.18.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<style>
.show{
display:black
}
.hidden{
display:none
}
</style>

</head>
<body>
<center>
<a href="${pageContext.request.contextPath }/toAdd.do">添加</a>
<div id="did" border="1px" >
<table id="tid" border="1px" :class="flag2">
	<tr>
	<td>
选择
	</td>
		<td>编号</td>
		<td>姓名</td>
		<td>年龄</td>
		<td>性别</td>
		<td>部门</td>
		<td>生日</td>
		<td>图片</td>
		<td>操作</td>
	</tr>
	<tr v-for="(stu,index) in slist">
		<td>
		<input type="checkbox" v-model="ids" :value="stu.sid">
		</td>
		<td v-text="stu.sid"></td>
		<td v-text="stu.sname">姓名</td>
		<td v-text="stu.age">年龄</td>
		<td v-text="stu.sex">性别</td>
		<td v-text="stu.dept.dname"></td>
		<td v-text="format(stu.birthday)"></td>
		<td>图片</td>
		<td><input type="button" @click="toUpdate(index)" value="修改"></td>
	</tr>
	
</table>
<input type="button" @click="del" value="删除">
<form  id="fid"  :class="flag1">
<input type="hidden" v-model="stu.sid"><br>
姓名：
<input type="text" v-model="stu.sname">
<br>年龄：
<input type="text" v-model="stu.age">
<br>性别：
<input type="radio" value="男" v-model="stu.sex">男
<input type="radio" value="女" v-model="stu.sex">女
<br>部门：
<select v-model="stu.depid">
<option v-for="dept in dList" :value="dept.depid" v-text="dept.dname"></option>
</select>

<br>生日：
<input type="date" v-model="stu.birthday"><br>
图片：<input type="file">
<br>
<input type="button" @click="update" value="修改">
</form>

</div>
</center>
<script type="text/javascript">
	var table = new Vue({
		el:"#did",
		data:{
			slist:[],
			stu:{},
			dList:[],
			flag1:'hidden',
			flag2:'show',
			ids:[]
	
		},
		created(){
			axios.post("${pageContext.request.contextPath }/findAll.do").then(function(res){
				
				table.slist = res.data;
			});
			axios.post("${pageContext.request.contextPath }/findDept.do").then(function(res){
				table.dList = res.data;
			})
		},
		methods:{
			format(datetime){
				var year = new Date(datetime).getFullYear();
				var month1= new Date(datetime).getMonth()+1;
				var month = month1<10?"0"+month1:month1;
				var date = new Date(datetime).getDate()<10?"0"+new Date(datetime).getDate():new Date(datetime).getDate();
				return  year+"-"+month+"-"+date
			},
			toUpdate(i){
				this.stu = this.slist[i];
				this.stu.birthday = this.format(this.stu.birthday);
				this.flag1="show";
				this.flag2="hidden";
				
			},
			update(){
			axios.post("${pageContext.request.contextPath }/updateStu.do",table.stu).then(function(res){
				if(res.data>0){
					table.flag1="hidden";
					table.flag2="show";
					location.href="${pageContext.request.contextPath }/toShow.do"
				};
			})	
				
			},
			del(){
				axios.post("${pageContext.request.contextPath }/delStu.do",this.ids).then(function(res){
				if(res.data>0){
					location.href="${pageContext.request.contextPath }/toShow.do";
				};	
					
				})
			}
		}
	})
	
	

	$(function(){
		
		//:gt(0)意思是索引>0开始
		$("tr:even:gt(0)").css("background-color","red")
		$("tr:odd").css("background-color","burlywood")
					
	})

</script>
</body>
</html> 