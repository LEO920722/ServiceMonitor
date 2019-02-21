
var pageNow=1;//分页时当前显示的是第几页
var json;
/**
 * 设置弹窗(提交的内容最好不要超过7项) 用于用户的提交模块
 * 必设项  name="form" form为固定值
 *        id="id"  id为传给后端的key值
 *  
 * 
 */
function openadd(title) {
    $("#myModalLabel").text(title);
    $("#userName").attr("readonly", false);
    $("input").val("");
    $("#queryResult").html("");
    $("#addModal").modal("show");
    $("#add").show();
    $("#edt").hide();
}
/**
 * 当点击弹出框中的查询的时候 开始查询
 * 
 */
function add() {
	pageNow=1;//点击查询的时候将pageNow至为1 从第一页开始
	json="";//将之前的数据清空
	var count=0;
    var formdata='{';
    var inputArray=$("input[name='form']");//取到所有的input text 并且放到一个数组中  
    inputArray.each(//使用数组的循环函数 循环这个input数组  
        function (){  
            var input =$(this);//循环中的每一个input元素  
            var key=input.attr("id");//获取每一个id的名字  并将来作为json的key值
    		var value=input.val();//获取每一个标签的内容    作为value
    		if (value == "") {//如果遍历到的这一项为空 就计数加一 用来统计是否都没有填写
    			count++;
    		}
    		formdata+=key+':"'+value+'",';//将key 和value 值全部保存到json数组中
        }  
    )  
    var userType=$("#user_Type  option:selected").text();
    if(count==inputArray.length&&userType==""){//如果空的条数 为总的条数 
    	//openadd("请至少填写一项");
    	$("#addModal").effect('shake', { times: 1 }, 100);
    	  //$( "#addModal" ).effect( "shake" );
    	$("#queryResult").html("--请至少填写一项");
    	$("#queryResult").css({"color": "red"});
    	//layer.tips('请至少填写一项', '#queryResult');
    	return false;
    }
    formdata+='user_Type:"'+userType+'",';
    formdata+='pageNumber:"'+pageNow+'"}';//加入当前点击的页数
    formdata=formdata.substring(0,formdata.length-1)+'}';//拼接成一个json
    json=eval('(' + formdata + ')');//转化为
//    json= json.replace(/\\/g, "");
//    json=JSON.stringify(json);
//    alert(formdata);
    console.log(json);
    addDataToTable(json);
}
/**
 * 调用的实例
 * @param formData
 */
function addDataToTable(formData){
	var state = init({
		data:formData,
		url:"/CePing/QueryByPageAndOther",
		dataType:"json",
		showType:"return",
	    Deferred:false,
	    Callback:function(dta){
	    	if (!dta || !dta.rows || dta.rows.length <= 0) {//数据库没有数据 说明没有被爬取
	        	$("#addModal").effect('shake', { times: 1 }, 100);
	        	$("#queryResult").html("--没有合适的记录");
	        	$("#queryResult").css({"color": "red"});
	        	//layer.tips('没有合适的记录', '#queryResult');
	        	$("#tbody").html("");
	    	}
	    	else{//使用模板填充
//	    		ModelFilling(dta,"text/template","html","tbody");
	    		setFenYe(dta,10,20,"tbody");//使用分页模板  dta为数据 10为每一页的数据 20为最多显示20页 
	    	    $("#addModal").modal("hide");
	    	    $("input").val("");
	    	    $("#user_Type").val("");
//	    	    $(".pages").html("");//将下面的page去掉
	    	}
		}
	})

}

/**
 * 查询所有的记录
 */
function queryAll(){
	alert(1);
	json="";
	clickPage(1);
}
/**
 * 当点击分页的数字时
 * @param number
 */
function clickPage(number){
	if(typeof(json)=="undefined"||json==""){//如果json没有被定义或者是点击查询全部的时候就 从新定义json
		json='{"pageNumber":"'+number+'"}';
		json=eval('(' + json + ')');//转化为json格式
	}else{
		json.pageNumber=number; //如果被定义就把传入的number传进去
	}
	pageNow=number;//将默认页设置为当前页
	var state = init({
		data:json,
		url:"/CePing/QueryByPageAndOther",
		dataType:"json",
		showType:"return",
	    Deferred:false,
	    Callback:function(dta){
	    	setFenYe(dta,10,20,"tbody");//使用分页模板  dta为数据 10为每一页的数据 20为最多显示20页 
		}
	})
}
/** 设置页面的分页
 * 
 * @param dta
 * @param everyPageNum 每一页的数据
 * @param maxPageNumbers 最多让它分几页 超过的另外再显示\
 * @param container 需要填充的模板
 */
function setFenYe(dta,everyPageNum,maxPageNumbers,container){
	var count=dta.count;
	var pageHtml="";//用来填充数据的模板 拼接而成
	var pageNumber=Math.ceil(count/everyPageNum);//获取总页数除不断的加一 比如 5/2=3
	if(count<=everyPageNum){//如果总数据小于每一页的数据
		pageHtml="<a >共"+count+"条数据</a>";
	}
	else if(pageNumber<=maxPageNumbers&&pageNumber>1){//数据大于每一页的数据量小于最大的页数量 
		if(pageNow!=1){//如果当前页为不第一页就添加
			pageHtml="<a onclick='clickPage("+(pageNow-1)+")'>上一页</a>";
		}
		for(var i = 1; i<= pageNumber; i++){//依次遍历页数 进行拼接
			if(i==pageNow){
				pageHtml+="<b>"+i+"</b>"//如果点击页为当前页就新增这个样式
			}
			else{
				pageHtml += "<a onclick='clickPage("+i+")'>" + i + "</a>"; 
			}
		}
		if(pageNow!=pageNumber){//不为最后页就添加
			pageHtml+="<a onclick='clickPage("+(pageNow+1)+")'>下一页</a>";
		}
	}//数据分页大于maxPageNumbers
	else{
		if(pageNow!=1){//如果当前页为不第一页就添加
			pageHtml="<a onclick='clickPage("+(pageNow-1)+")'>上一页</a>";
		}
		var pageNumber=Math.ceil(count/10);//获取页数
		pageHtml+="<b>第"+pageNow+"页</b>";
		pageHtml+="<a>共"+pageNumber+"页</a>";
		if(pageNow!=pageNumber){//不为最后页就添加
			pageHtml+="<a onclick='clickPage("+(pageNow+1)+")'>下一页</a>";
		}
	}
	ModelFilling(dta,"text/template","html",container);//利用数据填充
	$("#pages").html(pageHtml);
	
}



/**
 * 查询某一个测评的结果 得分的情况 以及按照得分进行操作
 */
function queryOneRecord(){
	var state = init({
		data:{
			record_Id:getUrlParam(window.location,"record_Id")
		},
		url:"/CePing/QueryRecord",
		dataType:"json",
		showType:"return",
	    Deferred:false,
	    Callback:function(dta){
          var data=dta.rows[0];
          $("#user_TestType").html(data.User_TestType);
          $("#user_TestDate").html(data.User_TestDate);
          $("#user_TestTime").html(data.User_TestTime);
          $("#user_Name").html(data.User_Name);
          var result=data.User_TestResult;
          result= eval(result);  //转化为
          var resultHtml="";
          for(var i=0;i<result.length;i++)
    	  {
        	  resultHtml+="<div class='list-group'>"
				 +"<a class='list-group-item active' href='#'>"+result[i].title+"</a>"
				+"<div class='list-group-item'>得分："+result[i].score
				+"</div><div class='list-group-item'><h4 class='list-group-item-heading'>"
					+result[i].result+"</h4></div></div>";
    	  }
          $("#user_TestResult").html(resultHtml);
		}
	})
}
/**
 * 管理员登陆
 * @returns {Boolean}
 */
function adminLogin(){
	var admin_Name=$("#admin_Name").val();
	var admin_Password=$("#admin_Password").val();
	if(admin_Name==""||admin_Password==""){
//		$("#result").html("请填写完整");
//		$("#result").css({"color": "white"});
		return false;
	}
	
	var state = init({
		data:{
			admin_Name:admin_Name,
			admin_Password:admin_Password
		},
		url:"/CePing/AdminLogin",
		dataType:"json",
		showType:"return",
	    Deferred:false,
	    Callback:function(dta){
	    	if (!dta || !dta.rows || dta.rows.length <= 0) {//数据库没有数据 说明没有被爬取
	    		$("#result").html("用户名或者密码错误");
	    		$("#result").css({"color": "white"});
	        	$("#result").effect('shake', { times: 1 }, 100);
	    	}
	    	else{
	    		window.location.href="admin.html?admin_Name="+admin_Name;
	    	}
		}
	})
}
  
function  getAdminName(){
	var admin_Name=getUrlParam(window.location,"admin_Name");
	$("#admin_Name").html(admin_Name);
}
