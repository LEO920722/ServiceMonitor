
var result;// 杩斿洖缁撴灉
/**
 * 
 */

/**
 * 鍒濆鍖栧叆鍙�
 * 
 * @param options
 *            鍒濆鍖栭�夐」
 * @returns 杩斿洖缁撴灉锛堜粎showType涓簉eturn鏃舵墠鏈夎繑鍥炲�硷級
 */
function init(options) {

	var data = "";// 璇锋眰鍙傛暟
	var dataType = "json";// 鍚庡彴杩斿洖鐨勬暟鎹被鍨嬶紙榛樿涓簀son锛�
	var showType = "model";// 杩斿洖鏁版嵁鐨勫睍鐜板舰寮忥紙榛樿涓簃odel锛� model 妯℃澘绫诲～鍏咃紝閫傜敤浜庡琛�   bind 鏁版嵁缁戝畾鍒板厓绱犱腑锛岄�傜敤浜庝竴琛�
	var template_Name = "text/template";// 妯℃澘绫诲悕锛堥粯璁や负text/template锛変粎showType涓簃odel鏃堕渶瑕佽缃�
	var fillType = "html";// 鏁版嵁濉厖鏂瑰紡锛宎ppend杩藉姞濉厖 html瑕嗙洊濉厖锛堥粯璁わ級 浠卻howType涓簃odel鏃堕渶瑕佽缃�
	var container = "content";// 鏁版嵁灞曠幇瀹瑰櫒id鍚� 锛堥粯璁や负content锛変粎showType涓簃odel鏃堕渶瑕佽缃�
	var url = "";// 璇锋眰URL
	var async=false;
	var Deferred = true; //寤舵椂鍔犺浇鏍囪瘑锛岄粯璁や笉浣跨敤
	var Callback;
	var finallyFun;
	console.log("init");
	/*鍒濆鍖栭厤缃弬鏁帮紝灏哾ata涓殑鏁版嵁鎷兼帴鎴愬瓧绗︿覆*/
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
	
	if(Deferred){//褰撹姹傜洿鎺ヨ繑鍥炴暟鎹椂
		postRequest_Return(url,data,dataType,showType,template_Name,fillType,container);
		
		return result;
	}else{
		/*鍙戝嚭璇锋眰锛岃姹俇RL*/
		$.when(postRequest(url,data,dataType)).done(function(dta){ 
			/* 鏍规嵁灞曠幇褰㈠紡纭畾杩斿洖浣曠缁撴灉 */
			switch (showType) {
			case "return":
				Callback(dta);
				break;
			case "model":
				ModelFilling(dta, template_Name,fillType,container);// 浣跨敤妯℃澘绫诲～鍏�
				break;
			case "bind":
				BoundData(dta.rows[0]);// 缁戝畾鏁版嵁鍒扮浉搴旂殑瀹瑰櫒涓�
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
 * 寮傛璇锋眰鏂瑰紡
 * @param para
 *            璇锋眰鍙傛暟
 * @param request_url
 *            璇锋眰URL
 * @param dataType
 *            杩斿洖鏁版嵁绫诲瀷
 * @param showType
 *            鏁版嵁灞曠幇褰㈠紡
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
			defer.resolve(dta);//灏哾efer瀵硅薄鐨勬墽琛岀姸鎬佷粠"鏈畬鎴�"鏀逛负"宸插畬鎴�"
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
			/* 鏍规嵁灞曠幇褰㈠紡纭畾杩斿洖浣曠缁撴灉 */
			switch (showType) {
			case "return":
				result = dta;
				break;
			case "model":
				result = ModelFilling(dta, template_Name,fillType,container);// 浣跨敤妯℃澘绫诲～鍏�
				break;
			case "bind":
				result = BoundData(dta.rows[0]);// 缁戝畾鏁版嵁鍒扮浉搴旂殑瀹瑰櫒涓�
				break;
			default:
				;
			}
		}
	});
}

/**
 * 浣跨敤妯℃澘绫诲～鍏呭睍鐜版暟鎹�
 * 
 * @param dta
 *            寰呭睍绀烘暟鎹�
 * @returns {String}绫诲瀷鐨勯敊璇俊鎭�
 */
function ModelFilling(dta,template_Name,fillType,container) {
	console.log(dta);
	if (!dta || !dta.rows || dta.rows.length <= 0) {
		console.log("dta is null");
		return "dta is null";
	}
	// 鑾峰彇妯℃澘涓婄殑HTML
	var html = $('script[type="' + template_Name + '"]').html();
	// 瀹氫箟涓�涓暟缁勶紝鐢ㄦ潵鎺ユ敹鏍煎紡鍖栧悎鐨勬暟鎹�
	var arr = [];
	// `1瀵规暟鎹繘琛岄亶鍘�

	var count=1;
	$.each(dta.rows, function(i, o) {
		// 杩欓噷鍙栧埌o灏辨槸涓婇潰rows鏁扮粍涓殑鍊�, formatTemplate鏄渶寮�濮嬪畾涔夌殑鏂规硶.
		arr.push(formatTemplate(o, html,count));
		count++;
	});
	// 濂戒簡锛屾渶鍚庢妸鏁扮粍鍖栨垚瀛楃涓诧紝骞舵坊鍔犲埌table涓幓銆�
	if (fillType == "html")
		$('#' + container).html(arr.join(''));
	else if (fillType == "append")
		$('#' + container).append(arr.join(''));
	// 璧板畬杩欎竴姝ュ叾瀹炲氨瀹屾垚浜嗭紝涓嶄細鍚э紝杩欎箞绠�鍗曪紝涓嶉敊锛屽氨鏄繖涔堢畝鍗�!! 涓嶄俊灏辫嚜宸卞姩鎵嬪幓璇曡瘯!
	return "ok";
}

/**
 * 灏嗘暟鎹粦瀹氬埌鐩稿簲鐨勫厓绱犱腑
 * 
 * @param dta
 *            寰呯粦瀹氱殑鏁版嵁
 */
function BoundData(dta) {
	console.log(dta);
	if (!dta || dta.length <= 0) {
		console.log("dta is null");
		return "dta is null";
	}
	var html = $("html").html();
	/*鍦ㄥ綋鍓嶉〉闈紝鍖归厤鎵�鏈夎{}鎷捣鏉ョ殑鍐呭锛屽皢鍖归厤缁撴灉浠ユ暟缁勫舰寮忓瓨鍌�*/
	var name = html.match(/{(\w+)}/g);
	var count = 0;
	var temp = "";
	/*濡傛灉鍖归厤缁撴灉涓嶄负绌猴紝鍒欐牴鎹嫭鍙蜂腑鐨勫悕绉颁粠dta涓彇鍑虹浉搴旀暟鎹紝缁戝畾鍒拌鍏冪礌*/
	if (name != null) {
		console.log(name.length);
		while (count < name.length) {
			temp = name[count] + "";
			/*鍙栧嚭鎷彿涓殑鍐呭*/
			var attr = temp.substring(1, temp.length - 1);
			console.log(temp + " " + attr);
			/*鏍规嵁鎷彿涓唴瀹硅幏鍙杁om鍏冪礌*/
			var dom = document.getElementById(temp);
			/*濡傛灉瀛樺湪璇om鍏冪礌锛屽垯缁戝畾鏁版嵁*/
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
 * 妯℃澘绫诲～鍏呯殑宸ュ叿绫伙紝鐢ㄦ暟鎹揩閫熷～鍏呮ā鏉跨被
 * 
 * @param dta
 *            寰呭～鍏呮暟鎹�
 * @param tmpl
 *            妯℃澘绫�
 * @param count
 *            濉厖璁℃暟鍣�
 * @returns 杩斿洖鏁版嵁濉厖鍚庢渶缁堢殑妯℃澘绫�
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
 * 鑾峰彇褰撳墠url鍚庣殑鍙傛暟鍊�
 * @param url 褰撳墠鐨剈rl
 * @param name 鍙傛暟鐨勫悕瀛�
 * @returns 鍙傛暟鐨勫��
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
 * 鑾峰緱URL涓殑鍙傛暟
 * @param name 鍙傛暟鍚�
 * @returns 鍙傛暟鍊�
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}