
var result;// 返回结果
/**
 * 
 */

/**
 * 初始化入口
 * 
 * @param options
 *            初始化选项
 * @returns 返回结果（仅showType为return时才有返回值）
 */
function init(options) {

	var data = "";// 请求参数
	var dataType = "json";// 后台返回的数据类型（默认为json）
	var showType = "model";// 返回数据的展现形式（默认为model） model 模板类填充，适用于多行   bind 数据绑定到元素中，适用于一行
	var template_Name = "text/template";// 模板类名（默认为text/template）仅showType为model时需要设置
	var fillType = "html";// 数据填充方式，append追加填充 html覆盖填充（默认） 仅showType为model时需要设置
	var container = "content";// 数据展现容器id名 （默认为content）仅showType为model时需要设置
	var url = "";// 请求URL
	var async=false;
	var Deferred = true; //延时加载标识，默认不使用
	var Callback;
	var finallyFun;
	console.log("init");
	/*初始化配置参数，将data中的数据拼接成字符串*/
	if (options.data != null && options.data != "") {
		data = JSON.stringify(options.data);
		data = data.substring(1, data.length - 1);
		data = data.replace(new RegExp("\"", "gm"), "");
		data = data.replace(new RegExp(":", "gm"), "=");
		data = data.replace(new RegExp(",", "gm"), "&");
	}
	if (options.dataType != null && options.dataType != "")
		dataType = options.dataType;
	if (options.showType != null && options.showType != "")
		showType = options.showType;
	if (options.template_Name != null && options.template_Name != "")
		template_Name = options.template_Name;
	if (options.fillType != null && options.fillType != "")
		fillType = options.fillType;
	if (options.container != null && options.container != "")
		container = options.container;
	if (options.url != null && options.url != "")
		url = options.url;
	if (options.async != null )
		async = options.async;
	if (options.Deferred != null)
		Deferred = options.Deferred;
	if (options.Callback != null )
		Callback = options.Callback;
	if (options.finallyFun != null )
		finallyFun = options.finallyFun;
	
	if(Deferred){//当请求直接返回数据时
		postRequest_Return(url,data,dataType,showType,template_Name,fillType,container);
		
		return result;
	}else{
		/*发出请求，请求URL*/
		$.when(postRequest(url,data,dataType)).done(function(dta){ 
			/* 根据展现形式确定返回何种结果 */
			switch (showType) {
			case "return":
				Callback(dta);
				break;
			case "model":
				ModelFilling(dta, template_Name,fillType,container);// 使用模板类填充
				break;
			case "bind":
				BoundData(dta.rows[0]);// 绑定数据到相应的容器中
				break;
			default:
				;
			}
			if(finallyFun!=null){
			finallyFun(dta);
			}
			$('.loader-item').fadeOut(); 
	        $('#pageloader').fadeOut('slow');
		});
	}
	
}

/**
 * 异步请求方式
 * @param para
 *            请求参数
 * @param request_url
 *            请求URL
 * @param dataType
 *            返回数据类型
 * @param showType
 *            数据展现形式
 */
function postRequest(url,data,dataType) {
	console.log("asynchronous Request:" + url);
	var defer = $.Deferred();
	$.ajax({
		url : url,
		type : 'post',
		data : data,
		cache : true,
		dataType : dataType,
		success : function(dta){
			console.log("asynchronous request data length is---"+JSON.stringify(dta));
			defer.resolve(dta);//将defer对象的执行状态从"未完成"改为"已完成"
		}
	});
	return defer.promise();
}


/**
 * @param url
 * @param data
 * @param dataType
 * @param showType
 * @param template_Name
 * @param fillType
 * @param container
 */
function postRequest_Return(url,data,dataType,showType,template_Name,fillType,container) {
	console.log("synchronous Request:" + url);
	$.ajax({
		url : url,
		type : 'post',
		data : data,
		cache : true,
		async : false,
		dataType : dataType,
		success : function(dta){
			console.log("synchronous request data is---"+JSON.stringify(dta) );
			/* 根据展现形式确定返回何种结果 */
			switch (showType) {
			case "return":
				result = dta;
				break;
			case "model":
				result = ModelFilling(dta, template_Name,fillType,container);// 使用模板类填充
				break;
			case "bind":
				result = BoundData(dta.rows[0]);// 绑定数据到相应的容器中
				break;
			default:
				;
			}
		}
	});
}

/**
 * 使用模板类填充展现数据
 * 
 * @param dta
 *            待展示数据
 * @returns {String}类型的错误信息
 */
function ModelFilling(dta,template_Name,fillType,container) {
	console.log(dta);
	if (!dta || !dta.rows || dta.rows.length <= 0) {
		console.log("dta is null");
		return "dta is null";
	}
	// 获取模板上的HTML
	var html = $('script[type="' + template_Name + '"]').html();
	// 定义一个数组，用来接收格式化合的数据
	var arr = [];
	// `1对数据进行遍历

	var count=1;
	$.each(dta.rows, function(i, o) {
		// 这里取到o就是上面rows数组中的值, formatTemplate是最开始定义的方法.
		arr.push(formatTemplate(o, html,count));
		count++;
	});
	// 好了，最后把数组化成字符串，并添加到table中去。
	if (fillType == "html")
		$('#' + container).html(arr.join(''));
	else if (fillType == "append")
		$('#' + container).append(arr.join(''));
	// 走完这一步其实就完成了，不会吧，这么简单，不错，就是这么简单!! 不信就自己动手去试试!
	return "ok";
}

/**
 * 将数据绑定到相应的元素中
 * 
 * @param dta
 *            待绑定的数据
 */
function BoundData(dta) {
	console.log(dta);
	if (!dta || dta.length <= 0) {
		console.log("dta is null");
		return "dta is null";
	}
	var html = $("html").html();
	/*在当前页面，匹配所有被{}括起来的内容，将匹配结果以数组形式存储*/
	var name = html.match(/{(\w+)}/g);
	var count = 0;
	var temp = "";
	/*如果匹配结果不为空，则根据括号中的名称从dta中取出相应数据，绑定到该元素*/
	if (name != null) {
		console.log(name.length);
		while (count < name.length) {
			temp = name[count] + "";
			/*取出括号中的内容*/
			var attr = temp.substring(1, temp.length - 1);
			console.log(temp + " " + attr);
			/*根据括号中内容获取dom元素*/
			var dom = document.getElementById(temp);
			/*如果存在该dom元素，则绑定数据*/
			if (dom != null&&dta[attr]!=null)
				{
				dom.innerHTML = dta[attr];
				dom.value=dta[attr];
				dom.setAttribute("src", dta[attr]);
				$(dom).attr("placeholder",dta[attr])
				}
			count++;
			temp = "";
		}
	}
}

/**
 * 模板类填充的工具类，用数据快速填充模板类
 * 
 * @param dta
 *            待填充数据
 * @param tmpl
 *            模板类
 * @param count
 *            填充计数器
 * @returns 返回数据填充后最终的模板类
 */
function formatTemplate(dta, tmpl,count) {
	var format = {
		name : function(x) {
			return x
		}
	};
	if(count!=null){
	 tmpl=tmpl.replace(/{(count)+}/,count);
	}	
	return tmpl.replace(/{(\w+)}/g, function(m1, m2) {
		if (!m2||(dta[m2]==null)){
			console.log(m2+"--"+dta[m2]);
			return "";
		}
		return (format && format[m2]) ? format[m2](dta[m2]) : dta[m2];
	})
}


/**
 * 获取当前url后的参数值
 * @param url 当前的url
 * @param name 参数的名字
 * @returns 参数的值
 */
function getUrlParam(url,name){
	
    var pattern = new RegExp("[?&]" + name +"\=([^&]+)","g");
    var matcher = pattern.exec(url);
    var items = null;
    if(matcher != null){
        try{
            items = decodeURIComponent(decodeURIComponent(matcher[1]));   
        }catch(e){
            try{
                items = decodeURIComponent(matcher[1]);
            }catch(e){
                items = matcher[1];
            }
        }
    }
    
    return items;
}

/**
 * 获得URL中的参数
 * @param name 参数名
 * @returns 参数值
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}