/**
 * login.js 封装登录和注册处理
 */
//log_in.html主处理
$(function(){
		//给登录按钮绑定单击处理
		$("#login").click(checkLogin);
		//给注册按钮绑定单击处理
		$("#regist_button").click(registerUser);
});
function checkLogin(){
				//console.log(1);
				var ok=true;
				//清空以前提示信息
				$('#count_span').html("");
				$('#pwd_span').html("");
				//获取请求参数
				var name=$('#count').val().trim();
				var password=$('#password').val().trim();
				//检测参数格式
				if(name==""){
					ok=false;
					$('#count_span').html("用户名为空");
				}
				if(password==""){
					ok=false;
					$('#pwd_span').html("密码为空");
				}
				//发送Ajax请求
	
				if(ok){
					
					$.ajax({
						url:"user/login.do",type:"post",data:{"name":name,"password":password},dataType:"json",
						success:function(result){//成功	
							if(result.status==0){
								var user=result.data;
								addCookie("uid",user.cn_user_id,2);
								addCookie("uname",user.cn_user_name,2);
								window.location.href="edit.html";
							}else if(result.status==1){//用户名错误
								console.log(3);
								$('#count_span').html("用户名错误");
							}else if(result.status==2){//密码错误
								console.log(2);
								$('#pwd_span').html("密码错误");
							}
						},
						error:function(){
							alert("登陆异常");
						}
					});	
					
				}
				
			}

function registerUser(){
	var ok=true;
	//获取请求参数
	var name=$("#regist_username").val().trim();
	var nick=$("#nickname").val().trim();
	var password=$("#regist_password").val().trim();
	var f_password=$("#final_password").val().trim();
	//格式检查
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	if(name==""){
		ok=false;
		$("#warning_1").show();
		$("#warning_1 span").html("用户名为空");
	}
	if(password==""){
		ok=false;
		$("#warning_2").show();
		$("#warning_2 span").html("密码为空 ");
	}else if(password.length<6){
		ok=false;
		$("#warning_2").show();
		$("#warning_2 span").html("密码长度太短");
	}
	if(f_password==""){
		ok=false;
		$("#warning_3").show();
		$("#warning_3 span").html("确认密码为空");
	}else if(f_password!=password){
		ok=false;
		$("#warning_3").show();
		$("#warning_3 span").html("与密码不一致");
	}
	//发送Ajax请求
	if(ok){
		$.ajax({
			url:"user/add.do",type:"post",data:{"name":name,"password":password,"nick":nick},dataType:"json",
			success:function(result){
				if(result.status==0){//成功
					alert(result.message);
					$("#back").click();
				}else if(result.status==1){//用户名被占用
					$("#warning_1").show();
					$("#warning_1 span").html(result.message);
				}
			},
			error:function(){
				alert("注册异常");
			}
		});		
	}
}
