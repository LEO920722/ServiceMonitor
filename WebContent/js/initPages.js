/**
 * 请配合init.js 
 *      jquery.js
 * 使用
 * 服务器需要传进来的数据 和之前一样(只需在后面加入 count 也就是查询的条数)
 *                             {
 *                               "rows":[
 *                                            {"username:","xuteng"},
 *                                            {"username:","zhengxinqi"},
 *                                            {"username:","leiguang"}
 *                                      ],
 *                               "count":12
 *                              }
 */
var pageNow=1;//分页时当前显示的是第几页 一般点击之后就记下了 以免点击下一页的时候忘记了
var json;//这是传给后台的数据 如果有就仔细写在data 里面
var url;//这是需要请求的url
var container="tbody";//这是查询完毕数据填充的模板
var everyPageNum=10;//这是每一页显示多少的数据 默认为10条数据
var maxPageNumbers=20;//这是最多显示多少页 当超过后将不再显示 分页 因为屏幕会撑爆
var pageContainer="pages";//这是显示分页的容器 在页面中写一下

function openadd(title) {
    $("#myModalLabel").text(title);
    $("#userName").attr("readonly", false);
    $("input").val("");
    $("select").val("");
    $("#queryResult").html("");
    $("#addModal").modal("show");
    $("#add").show();
    $("#edt").hide();
}
/**
 * 调用的实例1
 * 查询所有的记录
 */
function queryAll(){
    initData({
        url:"/CePing/QueryByPageAndOther",
        everyPageNum:15,
        maxPageNumbers:20,
        container:"tbody", //设置内容显示的容器
        pageContainer:"pages"//id为pages的div 容器
    })
}
/**
 */
/** 调用的实例2
 * 按条件查询的记录
 */
function querySome() {
    initData({
        data:{
            user_Tel:$("#user_Tel").val(),
            user_Name:$("#user_Name").val(),
            user_Type:$("#user_Type").val(),
            user_Class:$("#user_Class").val(),
            user_TestDate:$("#user_TestDate").val(),
            user_TestType:$("#user_TestType").val()
        },
        url:"/CePing/QueryByPageAndOther",
        everyPageNum:15,
        maxPageNumbers:20,
        container:"tbody", //设置内容显示的容器
        pageContainer:"pages"//id为pages的div 容器
    })

}
/**
 * 
 * @param options
 */
function initData(options){
    pageNow=1;//点击查询的时候将pageNow至为1 从第一页开始
    json ="";//将之前的数据清空
    json = options.data;
    url = options.url;
    container = options.container;
    everyPageNum = options.everyPageNum;
    maxPageNumbers = options.maxPageNumbers;
    pageContainer = options.pageContainer;
    addDataToTable(pageNow);
}
/**
 * 调用的实例
 * @param formData
 */
function addDataToTable(number){
    if(typeof(json)=="undefined"||json==""){//如果json没有被定义也就是是点击查询全部的时候就 从新定义json
        json='{"pageNumber":"1","everyPageNum":"'+everyPageNum+'"}';
        json=eval('(' + json + ')');//转化为json格式
    }else{
        json.pageNumber=number; //如果被定义就把传入的number传进去
        json.everyPageNum=everyPageNum;
//        alert(json.User_Tel);
//        json=eval('(' + json + ')');//转化为json格式
    }
    pageNow=number;//将默认页设置为当前页
    var state = init({
        data:json,
        url:url,
        dataType:"json",
        showType:"return",
        Deferred:false,
        Callback:function(dta){
            if (!dta || !dta.rows || dta.rows.length <= 0) {//数据库没有数据 说明没有被爬取
                $("#"+container+"").html("");//让容器置空
                $("."+pageContainer+"").html("");
                shack("没有找到合适的数据");
                return false;
            }
            else{//使用模板填充
                setFenYe(dta,everyPageNum,maxPageNumbers,container);//使用分页模板  dta为数据 10为每一页的数据 20为最多显示20页 
                $("#user_Type").val("");
                 $("#addModal").modal("hide");
            }
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
            pageHtml="<a onclick='addDataToTable("+(pageNow-1)+")'>上一页</a>";
        }
        for(var i = 1; i<= pageNumber; i++){//依次遍历页数 进行拼接
            if(i==pageNow){
                pageHtml+="<b>"+i+"</b>"//如果点击页为当前页就新增这个样式
            }
            else{
                pageHtml += "<a onclick='addDataToTable("+i+")'>" + i + "</a>"; 
            }
        }
        if(pageNow!=pageNumber){//不为最后页就添加
            pageHtml+="<a onclick='addDataToTable("+(pageNow+1)+")'>下一页</a>";
        }
    }//数据分页大于maxPageNumbers
    else{
        if(pageNow!=1){//如果当前页为不第一页就添加
            pageHtml="<a onclick='addDataToTable("+(pageNow-1)+")'>上一页</a>";
        }
        var pageNumber=Math.ceil(count/10);//获取页数
        pageHtml+="<b>第"+pageNow+"页</b>";
        pageHtml+="<a>共"+pageNumber+"页</a>";
        if(pageNow!=pageNumber){//不为最后页就添加
            pageHtml+="<a onclick='addDataToTable("+(pageNow+1)+")'>下一页</a>";
        }
    }
    ModelFilling(dta,"text/template","html",container);//利用数据填充
    $("."+pageContainer+"").html(pageHtml);
}

function shack(warningMsg){
    $("#addModal").effect('shake', { times: 1 }, 100);
      //$( "#addModal" ).effect( "shake" );
    $("#queryResult").html("--"+warningMsg);
    $("#queryResult").css({"color": "red"});
}