/**
 * 
 */
/**
 * 利用拼接的方式将数据加入 当数据大于10条的时候
 */
function fillDataToTable(dta){
	var data=dta.rows[0];
	var tableHtml="";
	for(var i =0; i<data.length; i++){
		tableHtml+="<tr ><th><font color='#FF8C00'>{User_TestType}</font></th>"
                         +"<th>{User_TestDate}</th>"
                         +"<th>{User_Name}</th>"
                         +"<th>{User_Type}</th>"
                         +"<th>{User_Class}</th>"
                         +"<th>{User_City}</th>"
                         +"<th>{User_School}</th>"
                         +"<th>{User_Tel}</th>"
                         +"<th width='10%'><a href='recordDetail.html?record_Id={Record_Id}'>测评结果</a></th>"
                   +"</tr>"
	}
}