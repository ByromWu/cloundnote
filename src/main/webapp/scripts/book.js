/**book.js封装笔记本相关处理 **/
//确定创建笔记本
function addBook(){
    					
	//获取请求参数
	var name=$("#input_notebook").val().trim();
	var userId=getCookie("uid");
	//格式检查
	if(name==""){
		$("#notebook_span").html("笔记本名为空");
	}else if(userId==null){
		window.location.href="log_in.html";
	}else{	//发送ajax请求
		$.ajax({
			url:"book/add.do",type:"post",data:{"name":name,"userId":userId},dataType:"json",
			success:function(result){
				//关闭对话框
				closeAlertWindow();
				//生成笔记本li元素
				var bookId=result.data.cn_notebook_id;
				var bookName=result.data.cn_notebook_name;
				createBookLi(bookId,bookName)
				//提示成功
				alert(result.message);
			},
			error:function(){
				alert("创建笔记本异常");
			}
		});
	}

}

function loadUserBooks(){
	//获取请求参数
	var userId=getCookie("uid");
	//alert(userId);
	//检查格式
	if(!userId){
		window.location.href="log_in.html";
	}else{
		//发送Ajax请求
		$.ajax({
			url:"book/loadbooks.do",type:"post",data:{"userId":userId},dataType:"json",
			//async:false,
			success:function(result){
				
				if(result.status==0){
					
					//读取返回的笔记本集合
					var books=result.data;
					for(var i=0;i<books.length;i++){
						var bookId=books[i].cn_notebook_id;
						
						//console.log(bookId);
						var bookName=books[i].cn_notebook_name;
						createBookLi(bookId,bookName);
					}
				}
			},
			error:function(){
				alert("加载笔记本列表异常");
			}
		});
	}
}	
	
 //创建笔记本列表li元素
function createBookLi(bookId,bookName){
	//构建列表元素
	var sli='';
	sli+='<li class="online"> '+
				'<a>'+
					'<i class="fa fa-book" title="online" rel="tooltip-bottom">'+
					'</i>'+bookName
					+'</a> '+
			'</li>';
	//将bookId绑定到li元素上
	var $li=$(sli);
	$li.data("bookId",bookId);
	//将li元素添加到ul列表中
	$("#book_ul").append($li);
}