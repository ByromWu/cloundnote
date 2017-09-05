/** 封装笔记相关操作**/
//搜索分享笔记
function searchShareNote(event){
	 var code=event.keyCode;
	 if(code==13){
		 $("#pc_part_6 ul").empty();
		//显示搜索结果列表 其它列表隐藏
			
		$("#pc_part_2").hide();
		$("#pc_part_4").hide();
		$("#pc_part_6").show();
		$("#pc_part_7").hide();
		$("#pc_part_8").hide();
		keyword=$(this).val().trim();
		 //console.log(keyword);
	    searchSharePage(keyword,page);
	 }
}
//分页加载搜索笔记
function searchSharePage(keyword,page){
 $.ajax({
		url:"note/search_share.do",type:"post",data:{"keyword":keyword,"page":page},dataType:"json",
		success:function(result){
			if(result.status==0){
				
				//获取服务器返回的结果
				var shares=result.data;
				console.log(shares.length);
				//循环解析生成列表li元素
				for(var i=0;i<shares.length;i++){
					var shareId=shares[i].cn_share_id;
					var shareTitle=shares[i].cn_share_title;
					//console.log(shareTitle);
					//生成li元素
					var sli='';
					sli+='<li class="online"> '+
							'<a > '+
								'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+
								shareTitle+
								'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>'+
							'</a>'+
						'</li>'	
				
				//将noteId绑定到li元素上
				var $li=$(sli);
				$li.data("shareId",shareId);
				//将li元素添加到笔记本列表ul中
				
				$("#pc_part_6 ul").append($li);
				}
				
			}
			
		},
		error:function(){
			alert("搜索分享笔记失败");
		}
	 });
}
//分享笔记
function shareNote(){
	$li=$(this).parents("li");
	//console.log($("#note_ul a.checked").parent())
	var noteId=$li.data("noteId");
	console.log($li.text());
	//发送ajax请求
	$.ajax({
		url:"note/share.do",type:"post",data:{"noteId":noteId},dataType:"json",
		success:function(result){
			if(result.status==0){
				//添加分享图标
				var img='<i class="fa fa-sitemap"></i>';
				$li.find(".btn_slide_down").before(img);
				alert(result.message);
			}else if(result.status==1){
				alert(result.message);
			}
			
		},
		error:function(){
			alert("分享笔记异常");
		}
	});
}
//转移笔记
function moveNote(){
	var $li=$("#note_ul a.checked").parent();
	var noteId=$li.data("noteId");
	//获取要转入的笔记本Id
	var bookId=$("#moveSelect").val();
	$.ajax({
		url:"note/move.do",type:"post",data:{"noteId":noteId,"bookId":bookId},dataType:"json",
		success:function(result){
			if(result.status==0){
				//移除笔记
				$li.remove();
				alert(result.message);
			}
		},
		error:function(){
			alert("转移笔记失败");
		}
	});
}
//删除笔记
function deleteNote(){
	console.log(1);
	//获取请求参数
	var $li=$("#note_ul a.checked").parent();
	var noteId=$li.data("noteId");
	console.log(noteId);
	$.ajax({
		url:"note/delete.do",type:"post",data:{"noteId":noteId},dataType:"json",
		success:function(result){
			if(result.status==0){
				closeAlertWindow();
				//删除li
				$li.remove();
				//提示成功
				alert(result.message);
			}
		},
		error:function(){
			alert("删除笔记失败");
		}
	});
}
//隐藏笔记菜单
function hideMenu(){
	$("#note_ul div").hide();
}
//弹出笔记菜单操作
function popNoteMenu(){
	//隐藏所有笔记惨淡
	$("#note_ul div").hide();
	//显示点击的笔记菜单
	var $menu=$(this).parent().next();
	$menu.slideDown(1000);
	//设置点击笔记选中效果
	$("#note_ul a").removeClass("checked");
	$(this).parent().addClass("checked");
	//取消事件向li,body冒泡
	return false;
	
}
//确定创建笔记
function addNote(){
	//获取请求参数
	var userId=getCookie("uid");//userId
	console.log(userId);
	var noteTitle=$("#input_note").val().trim();
	$li=$("#book_ul a.checked").parent();
	var bookId=$li.data("bookId");
	console.log(bookId);
	if(userId==""){
		window.location.href="log_in.html";
	}else if(noteTitle==""){
		$("#note_span").html("笔记名称为空");
	}else{
		$.ajax({
			url:"note/add.do",type:"post",data:{"userId":userId,"noteTitle":noteTitle,"bookId":bookId},dataType:"json",
			success:function(result){
				//关闭对话框
				closeAlertWindow();
				//生成笔记列表li
				var noteId=result.data;
				console.log(noteId);
				createNoteLi(noteId,noteTitle);
				//弹出提示
				alert(result.message);
			},
			error:function(){
				alert("创建笔记异常");
			}
		});
	}
	
}

//根据笔记本ID加载笔记信息
function loadBookNotes() {
    //切换列表显示
	$("#pc_part_2").show();
	$("#pc_part_4").hide();
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	//获取请求参数
    //设置笔记本li选中效果
    $("#book_ul a").removeClass("checked");
    $(this).find("a").addClass("checked");
    var bookId=$(this).data("bookId");
    //alert(bookId);
    //格式检查
    //发送ajax请求
    $.ajax({
	    url:"note/loadnotes.do",type:"post",data:{"bookId":bookId},dataType:"json",
	    success:function(result){
	    						
		    if(result.status==0){
		    							
			    //清空原有笔记列表
			    //$("#note_ul li").remove();
			    $("#note_ul").empty();
				//获取服务器返回的笔记集合信息
				var notes=result.data;
				//console.log(notes.length);
				//循环生成笔记li元素
				for(var i=0;i<notes.length;i++){
				
				//获取笔记ID和笔记标题
				var noteId=notes[i].cn_note_id;
				var noteTitle=notes[i].cn_note_title;
				//创建笔记列表li元素
				createNoteLi(noteId,noteTitle);
				var typeId=notes[i].cn_note_type_id;
				if(typeId==2){
					var img='<i class="fa fa-sitemap"></i>';
					var $li=$("#note_ul li:last");
					$li.find(".btn_slide_down").before(img);
					}
				}
			}
		},
		error:function(){
			alert("加载笔记列表异常");
		}
	});

}

//创建笔记列表li元素
function createNoteLi(noteId,noteTitle){
	//创建一个笔记li元素
	var sli='';
	sli+='<li class="online"> '+
			'<a > '+
				'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+
				noteTitle+
				'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>'+
			'</a>'+
			'<div class="note_menu" tabindex="-1">'+
				'<dl>'+
					'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt> '+
					'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt> '+
					'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
				'</dl>'+
			'</div>'+
		'</li>'	
		//将noteId绑定到li元素上
		var $li=$(sli);
		$li.data("noteId",noteId);
		//将li元素添加到笔记本列表ul中
		$("#note_ul").append($li);
}
//根据笔记ID加载笔记信息
function loadNote(){
	$("#pc_part_3").show();
	$("#pc_part_5").hide();
	//获取请求参数
	var noteId=$(this).data("noteId");
	//设置笔记选中状态(重点)
	$("#note_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//alert(noteId);
	//格式检查
	//发送Ajax请求
	$.ajax({
		url:"note/load.do",type:"post",data:{"noteId":noteId},dataType:"json",
		success:function(result){
			if(result.status==0){
				var title=result.data.cn_note_title;//获取笔记标题
				var body=result.data.cn_note_body;	//获取笔记内容
				//设置到编辑区域
				$("#input_note_title").val(title);
				um.setContent(body);
			}
		},
		error:function(){
			alert("加载笔记异常");
		}
	});
}
//"保存笔记"按钮的处理
function updateNote(){
	//获取请求参数
	var title=$("#input_note_title").val().trim();
	var body=um.getContent();
	var $li=$("#note_ul a.checked").parent();
	var noteId=$li.data("noteId");
	//alert($li.length);
	//清空上次提示信息
	$("#note_title_span").html("");
	//格式检查	
	if($li.length==0){
		alert("请选择要保存的笔记");
	}else if(title==""){
		$("#note_title_span").html("<font color='red'>标题不能为空</font>");	
	}else{
		$.ajax({
			url:"note/update.do",type:"post",data:{"noteId":noteId,"title":title,"body":body},dataType:"json",
			success:function(result){
				if(result.status==0){
					//更新列表li中的标题(重点关注)
					var sli='';
					sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+
							title+
							'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>'+
					$li.find('a').html(sli);
					//提示成功
					console.log(result.message);
				}
			},
			error:function(){
				alert("保存笔记异常");
			}
		});
	}
}
