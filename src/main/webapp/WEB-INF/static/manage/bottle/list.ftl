<#assign menu="bottle">
<#assign submenu="bottle_list">
<#include "/manage/head.ftl">
<style type="text/css">
.pagination {
    border-radius: 4px;
    display: inline-block;
    margin: 0;
    padding-left: 0;
}

.howto, .nonessential, #edit-slug-box, .form-input-tip, .subsubsub {
    color: #666666;
}

.subsubsub {
    float: left;
    font-size: 12px;
    list-style: none outside none;
    margin: 8px 0 5px;
    padding: 0;
}

.form-group{
	width:100%;
}

.count{
	position:absolute ;
	right:0px;
}

.arrticle_status{
	float:left;
}
</style>


<script type="text/javascript">



</script>
</
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper">		
        	<!-- page start-->
            <section class="panel">	        	
                <div class="panel-body">
                	<div class="adv-table">
                    	<div role="grid" class="dataTables_wrapper" id="hidden-table-info_wrapper">
                            <table class="table table-striped table-advance table-hover">
                            	<thead>
                                	<tr>
										<th>名称</th>
										<th>状态</th>
										<th>地址</th>
										<th>标识符</th>
                						<th>查看二维码</th>
              						</tr>
                                </thead>
                            	<tbody role="alert" aria-live="polite" aria-relevant="all">
                            		<#list bottleList as e>
                            		<tr class="gradeA odd">
               							<td>
               								${e.name}
               							</td>
               							<td>
               								${e.status}               								
               							</td>
               							<td>
               								${e.location}
               							</td>
               							<td>
               								${e.identifier}
               							</td>
                            			<td>
                            				<input type="button" value="显示详情" onclick="showNegativeBalanceRule('${e.identifier}');" />
                            			</td>                                    	
                                	</tr>
                                	</#list>
                               	</tbody>
                              </table>
                           </div>
                           <div id="negativeBalanceSet">
							</div> 
                        </div>
                  </div>
              </section>
              <!-- page end-->
          </section>
		</section>		
<style type="text/css">
<!--
td.white {
	white-space: nowrap;
}
-->
</style>
<script type="text/javascript">
function editNegativeBalanceEmail(ruleId) {
		$('#negativeBalance_dlgEdit').show();
	    document.getElementById("editRuleId").value = ruleId;
	    var rule = {"ruleId":ruleId}
	    $.ajax({  
        type:"post",
        dataType:"text",  
        data:rule,
        url:"showNegativeBalanceEmail.action",
        success:function(data){
        	drawNegativeBalanceEmailDiv(data);
         },  	          
   	     error: function(data){
     		  alert('Show failed! errorMessage:' + data);
      	 }
    }); 
}
function showNegativeBalanceRule(identifier) {
	
	var rule = {"identifier":identifier}

	$('#negativeBalance_dlg').show();
	$.ajax({  
        type:"post",
        dataType:"text",  
        data:rule,
        url:"showTemplateOfBottle.json",
        success:function(data){
        	drawNegativeBalanceRuleDiv(data);
        	
         },  	          
   	     error: function(data){
     		  alert('Show failed! errorMessage:' + data);
      	 }
    }); 
}
function editNegativeBalanceRule(ruleId) {
	var target;
	
	var inputThreshold = "rule.threshold"+ruleId;
	var selectStatus = "rule.status"+ruleId;
	var editRuleButton = "editRuleButton"+ruleId;
  
   	target=document.getElementById(inputThreshold);
   	target.disabled = false;
   	target=document.getElementById(selectStatus);
   	target.disabled = false;
   	drawNegativeBalanceSaveButton(ruleId);
}
function saveNegativeBalanceRule(ruleId) {
	var target;
	
	var inputThreshold = "rule.threshold"+ruleId;
	var selectStatus = "rule.status"+ruleId;
	var editRuleButton = "editRuleButton"+ruleId;		
		var threshold = document.getElementById(inputThreshold).value;
		if (!testNegativeBalanceThreshold(threshold)) {
			alert("threshold("+threshold+") is not a valid data! ");
			return;
		}
		if (threshold.length > 6) {
			alert("The length of threshold("+threshold+") exceeds the maximum length allowed(6) ! ");
			return;
		}
		var obj = document.getElementById(selectStatus);
		var status = obj.options[obj.selectedIndex].value;
		var rule = {"ruleId":ruleId,"threshold":threshold,"status":status};
	    $.ajax({  
           type:"post",
           dataType:"json",  
           data:rule,
           url:"saveNegativeBalanceEmailAlertRule.action",
           success:function(data){
            if ("ok" == data.errorCode){
           	alert('Save successfully!');	
            	target=document.getElementById(inputThreshold);
            	target.disabled = true;
            	target=document.getElementById(selectStatus);
            	target.disabled = true;
            	drawNegativeEditButton(ruleId);
            } else{
               alert('Save failed! errorMessage:' + data.errorMessage);
            }
       }  	          
    }); 
}
function deleteNegativeBalanceRule(ruleId) {
	var del = confirm("Are you sure you want to permanently delete the data?");
		var rule = {"ruleId":ruleId};
	if (del == true) {
	    $.ajax({  
            type:"post",
            dataType:"json",  
            data:rule,
            url:"deleteNegativeBalanceEmailAlertRule.action",
            success:function(data){
             if ("ok" == data.errorCode){
            	alert('Delete successfully!');	
            	var target=document.getElementById("ruleTr"+ruleId);
            	target.remove();
            	target = $("tr[id^='ruleTr']");
            	if (target==null || target.length==0) {
            		var htmlTrD = "<tr><td colspan=\"6\" ><div style=\"color:#FF0000\">NO Data</div></td></tr>";
            		$(htmlTrD).appendTo($('#ruleTable'));
            	}
             } else{
                alert('Delete failed! errorMessage:' + data.errorMessage);
             }
        }  	          
     }); 
	} else {
		
	}
}
function addNegativeBalanceRule() {
	var partnerId = document.getElementsByName("operator.id")[0].value;
	
	var threshold = document.getElementById("addThresholds").value;
		if (!testNegativeBalanceThreshold(threshold)) {
			alert("threshold("+threshold+") is not a valid data! ");
			return;
		}
		if (threshold.length > 6) {
			alert("The length of threshold("+threshold+") exceeds the maximum length allowed(6) ! ");
			return;
		}
	var obj = document.getElementById("addStatus_negativeBalance");
	var status = obj.options[obj.selectedIndex].value;
	var emails = "";
	var addEmailList = $("input[id^='addEmail_negativeBalance']");
	for (var i=0;i<addEmailList.length;i++) {
	  if (null != addEmailList[i].value && ''!=addEmailList[i].value) {
   		if (!testEmail(addEmailList[i].value)) {
   			alert("Email("+addEmailList[i].value+") is not a email address! ");
   			return;
   		}
		emails = emails + addEmailList[i].value + ",";
	  }
	}
	var rule = {"partnerId":partnerId,"threshold":threshold,"status":status,"emailList":emails};
    $.ajax({  
        type:"post", 
        dataType:"json",  
        data:rule,
        url:"addNegativeBalanceEmailAlertRule.action",
        success:function(data){  
        	if ("ok" == data.errorCode){
        		alert('Add successfully!');	
           		$('#negativeBalance_dlgAdd').dialog('close');
           		showNegativeBalanceRule();
        	}            	            	
        	else{
        		alert('Add failed! errorMessage:' + data.errorMessage);
        	}
       },
  	   error: function(data){
 		  alert('Add failed! errorMessage:' + data.errorMessage);
  	   }
 	});
	
}
function addNegativeBalanceEmailTr(table) {
	    var ruleId = document.getElementById("editRuleId").value;
	var html = "<tr><td></td><td>" + "<input type=\"text\" style=\"width:150px;\" value=\"\" name=\"editNegativeBalanceEmail\" id=\"editNegativeBalanceEmail\"> " +
	"<a href=\"javascript:void(0)\" onclick=\"delNegativeBalanceRow(this.parentElement.parentElement);\" > " + 
	"<img src=\"css/icons/edit_remove.png\" border=\"0\" /> </a> "
	"</td></tr>";
	var targetObj = $(html).appendTo(table);
	$.parser.parse(targetObj.parent());
}
function addNegativeBalanceEmailTr_new(table) {
	var html = "<tr><td></td><td>" + "<input name=\"addEmail_negativeBalance\" id=\"addEmail_negativeBalance\" type=\"text\" value=\"\" style=\"width:150px;\"/>" +
	"<a href=\"javascript:void(0)\" onclick=\"delNegativeBalanceRow(this.parentElement.parentElement);\" > " + 
	"<img src=\"css/icons/edit_remove.png\" border=\"0\" /> </a> "
	"</td></tr>";
	var targetObj = $(html).appendTo(table);
	$.parser.parse(targetObj);
}

 function strToJson(str){
		var json = eval('(' + str + ')');
		return json;
	} 
function drawNegativeBalanceEmailDiv(data)
{
	$('#negativeBalanceEdit').html("");
	
	var json=strToJson(data);
	
	var htmlTableS = "<table id=\"emailTable\" name=\"emailTable\"> ";
	var htmlTableE = "</table> ";
	var htmlTrH = "<tr> <td>Send Email:</td> <td><a href=\"javascript:void(0)\" onclick=\"addNegativeBalanceEmailTr($('#emailTable'));\">"
	            + "<img src=\"css/icons/edit_add.png\" border=\"0\" /></a></td></tr> ";
	var htmlTrS = "<tr><td></td><td> ";
	var htmlTrE = "</td></tr> ";
	var htmlA = "<a href=\"javascript:void(0)\" onclick=\"delNegativeBalanceRow(this.parentElement.parentElement);\" > <img src=\"css/icons/edit_remove.png\" border=\"0\" /></a> ";
	var htmlI = "";
	var htmlSub = "";
	for(var index=0;index<json.length;index++){
		htmlI = "<input type=\"text\" style=\"width:150px;\" value=\""+json[index].email+"\" name=\"editNegativeBalanceEmail\" id=\"editNegativeBalanceEmail\"> ";
		htmlSub = htmlSub + htmlTrS + htmlI + htmlA + htmlTrE;
	}
	var html = htmlTableS + htmlTrH + htmlSub + htmlTableE
	$(html).appendTo($('#negativeBalanceEdit'));
}
function drawNegativeBalanceAddDiv() {
	var operatorName = document.getElementsByName("operator.partnername")[0].value;
	$('#negativeBalanceAdd').html("");
	var html = 
    "<table name=\"negativeBalance_addTable\" id=\"negativeBalance_addTable\">"+
    "  <tr>"+
    "    <td>Operator:</td>"+
    "    <td><input name=\"operator\" id=\"operator\" type=\"text\" value=\""+operatorName+"\" style=\"width:150px;\" disabled=\"true\" /></td>"+
    "  </tr>"+
    "  <tr>"+
    "    <td>Threshold:</td>"+
    "    <td><input name=\"addThresholds\" id=\"addThresholds\" type=\"text\" value=\"\" style=\"width:150px;\"/></td>"+
    "  </tr>"+
    "  <tr>"+
    "  <td>Status:</td>"+
    "  <td>"+
    "    <select name=\"addStatus_negativeBalance\" id=\"addStatus_negativeBalance\" style=\"width:150px;\"> "+
		"	<option value =\"0\" >Disabled</option> "+
		"	<option value =\"1\" >Active</option> "+
		"  </select>"+
    "  </td>"+
    "  </tr>"+
    "  <tr>"+
    "    <td>Send Email:</td>"+
    "    <td><a href=\"javascript:void(0)\" onclick=\"addNegativeBalanceEmailTr_new($('#negativeBalance_addTable'));\"> <img src=\"css/icons/edit_add.png\" border=\"0\" /></a></td>"+
    "  </tr>"+
    "  <tr>"+
    "    <td></td>"+
    "    <td>"+
    "      <input name=\"addEmail_negativeBalance\" id=\"addEmail_negativeBalance\" type=\"text\" value=\"\" style=\"width:150px;\"/>"+
		"		<a href=\"javascript:void(0)\" onclick=\"delNegativeBalanceRow(this.parentElement.parentElement);\" > <img src=\"css/icons/edit_remove.png\" border=\"0\" /></a> "+
    "    </td>"+
    "  </tr> "+
    "</table>";

	$(html).appendTo($('#negativeBalanceAdd'));
}
function showNegativeBalanceAddRule() {
	$('#negativeBalance_dlgAdd').dialog('open');
	drawNegativeBalanceAddDiv();
}
function drawNegativeEditButton(ruleId) {
	$('#tdOperator'+ruleId).html("");
	var htmlTd = 
		"<a name=\"editRuleButton"+ruleId+"\" id=\"editRuleButton"+ruleId+"\" class=\"easyui-linkbutton l-btn l-btn-small\" onclick=\"editNegativeBalanceRule("+ruleId+");\" data-options=\"iconCls:'icon-edit'\" href=\"javascript:void(0)\" group=\"\">"+
		"<span class=\"l-btn-left l-btn-icon-left\">"+
		"<span class=\"l-btn-text\">Edit&nbsp;</span>"+
		"<span class=\"l-btn-icon icon-edit\"> </span>"+
		"</span>"+
		"</a>"+
		"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		"<a name=\"deleteRuleButton"+ruleId+"\" id=\"deleteRuleButton"+ruleId+"\" class=\"easyui-linkbutton l-btn l-btn-small\" onclick=\"deleteNegativeBalanceRule("+ruleId+");\" data-options=\"iconCls:'icon-cut'\" href=\"javascript:void(0)\" group=\"\">"+
		"<span class=\"l-btn-left l-btn-icon-left\">"+
		"<span class=\"l-btn-text\">Delete</span>"+
		"<span class=\"l-btn-icon icon-cut\"> </span>"+
		"</span>"+
		"</a>";
		$(htmlTd).appendTo($('#tdOperator'+ruleId));

}
function drawNegativeBalanceSaveButton(ruleId) {
	$('#tdOperator'+ruleId).html("");
	var htmlTd = 
		"<a name=\"editRuleButton"+ruleId+"\" id=\"editRuleButton"+ruleId+"\" class=\"easyui-linkbutton l-btn l-btn-small\" onclick=\"saveNegativeBalanceRule("+ruleId+");\" data-options=\"iconCls:'icon-save'\" href=\"javascript:void(0)\" group=\"\">"+
		"<span class=\"l-btn-left l-btn-icon-left\">"+
		"<span class=\"l-btn-text\">Save</span>"+
		"<span class=\"l-btn-icon icon-save\"> </span>"+
		"</span>"+
		"</a>"+
		"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		"<a name=\"deleteRuleButton"+ruleId+"\" id=\"deleteRuleButton"+ruleId+"\" class=\"easyui-linkbutton l-btn l-btn-small\" onclick=\"deleteNegativeBalanceRule("+ruleId+");\" data-options=\"iconCls:'icon-cut'\" href=\"javascript:void(0)\" group=\"\">"+
		"<span class=\"l-btn-left l-btn-icon-left\">"+
		"<span class=\"l-btn-text\">Delete</span>"+
		"<span class=\"l-btn-icon icon-cut\"> </span>"+
		"</span>"+
		"</a>";
		$(htmlTd).appendTo($('#tdOperator'+ruleId));

}
function reDrawNegativeBalanceStatus(data) {
	var json=strToJson(data);
	var selectStatus = "";
   	var target;
	for(var index=0;index<json.length;index++){
		selectStatus = "rule.status"+json[index].ruleId;
		target=document.getElementById(selectStatus);
		if (json[index].status == target.options[0].value) {
			target.options[0].selected = 'selected';
		} else {
			target.options[1].selected = 'selected';
		}
		target.disabled = true;
	}
}
function drawNegativeBalanceRuleDiv(data) {
	$('#negativeBalanceSet').html("");
	
	var jsonData=strToJson(data);
	var json=jsonData.t;
	
	var htmlTableS = "<table id=\"ruleTable\" name=\"ruleTable\" border=\"1\" cellspacing=\"1\" cellpadding=\"1\" width=\"100%\" > ";
	var htmlTableE = "</table> ";
	var htmlTrH = "<tr> <td width=\"15%\" align=\"center\">模板名称</td> "+
		               "<td width=\"15%\" align=\"center\">条形码</td> "+
		               "<td width=\"15%\" align=\"center\">单价</td> "+
		               "<td width=\"15%\" align=\"center\">创建时间</td> "+
		               "<td width=\"40%\" align=\"center\">描述</td> </tr>";
	var htmlTrD = "";
	var number;
	for(var index=0;index<json.length;index++){
		number = index+1;
		htmlTrD = htmlTrD +
		"<tr> "+
		
		"<td align=\"center\">"+ json[index].name +"</td>"+ 
		"<td align=\"center\">"+ json[index].barCode +"</td>"+ 
		"<td align=\"center\">"+ json[index].price +"</td>"+ 
		"<td align=\"center\">"+ json[index].createTimeStr +"</td>"+ 
		"<td align=\"center\">"+ json[index].description +"</td>"+
	    "</tr> ";
	}
	if (htmlTrD == '') {
		htmlTrD = "<tr><td colspan=\"6\" ><div style=\"color:#FF0000\">NO Data</div></td></tr>";
	}
	var html = htmlTableS + htmlTrH + htmlTrD + htmlTableE
	$(html).appendTo($('#negativeBalanceSet'));
}


var testNegativeBalanceThreshold=function(value) {
  if (value==null || value=="") {
	  return false;
  }
  var reg = /^[+|-]?\d*\.?\d*$/;
 
  var result = reg.test(value);
  
  if(test=true){
	  var floatValue=parseFloat(value);
	  var intValue=parseInt(value);
	 
	  if(floatValue!=NaN){
		  if(floatValue>0){
			  return false;  
		  }  
	  }
	  if(intValue!=NaN){
		  if(intValue>0){
			  return false;  
		  }  
	  }
  }
 
  return result;
 }
var testEmail=function(value) {
 var reg=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
 var result = reg.test(value);
 return result;
}
function delNegativeBalanceRow(src) {  
    src.parentElement.deleteRow(src.rowIndex);  
}  
function saveNegativeBalanceEmails() {
	    var ruleId = document.getElementById("editRuleId").value;
	var emailList = $("input[id='editNegativeBalanceEmail']");
	if (emailList.length > 0) {
	   var emails = "";
	   for (var i=0;i<emailList.length;i++) {
		 if (null != emailList[i].value && ''!=emailList[i].value) {
	       if (!testEmail(emailList[i].value)) {
	         alert("Email("+emailList[i].value+") is not a email address! ");
	         return;
	       }
		   emails = emails + emailList[i].value + ",";
		 }
	   }
	}
	var rule = {"ruleId":ruleId,"emailList":emails};
   $.ajax({  
       type:"post", 
       dataType:"json",  
       data:rule,
       url:"saveNegativeBalanceEmails.action",
       success:function(data){  
       	if ("ok" == data.errorCode){
       		alert('Add successfully!');	
       		$('#negativeBalance_dlgEdit').dialog('close');
       		showNegativeBalanceRule();
       	}            	            	
       	else{
       		alert('Add failed! errorMessage:' + data.errorMessage);
       	}
      },
 	   error: function(data){
		  alert('Add failed! errorMessage:' + data.errorMessage);
 	   }
	});
}


function saveNegativeBalance() {
	    var ruleId = document.getElementById("editRuleId").value;
	var emailList = $("input[id='editNegativeBalanceEmail']");
	if (emailList.length > 0) {
	   var emails = "";
	   for (var i=0;i<emailList.length;i++) {
		 if (null != emailList[i].value && ''!=emailList[i].value) {
	       if (!testEmail(emailList[i].value)) {
	         alert("Email("+emailList[i].value+") is not a email address! ");
	         return;
	       }
		   emails = emails + emailList[i].value + ",";
		 }
	   }
	}
	var rule = {"ruleId":ruleId,"emailList":emails};
   $.ajax({  
       type:"post", 
       dataType:"json",  
       data:rule,
       url:"saveNegativeBalanceEmails.action",
       success:function(data){  
       	if ("ok" == data.errorCode){
       		alert('Add successfully!');	
       		$('#negativeBalance_dlgEdit').dialog('close');
       		showNegativeBalanceRule();
       	}            	            	
       	else{
       		alert('Add failed! errorMessage:' + data.errorMessage);
       	}
      },
 	   error: function(data){
		  alert('Add failed! errorMessage:' + data.errorMessage);
 	   }
	});
}
</script>	
<div> 
    <input name="operatorType" id="operatorType" type="hidden" value="Edit"/>
	<div id="negativeBalance_dlg" class="easyui-dialog" title="Negative Balance Alert Setting" style="width:800px;height:600px;padding:50px" 
	data-options=" 
	            iconCls: 'icon-search', 
	            closed: 'true', 
                modal: true,
	            toolbar: '#dlg-toolbar', 
	            buttons: '#dlg-buttons'"> 
		<div id="negativeBalanceSet">
		</div> 
	</div> 
	<div id="dlg-toolbar" style="padding:2px 0"> 
		<table cellpadding="0" cellspacing="0" style="width:100%"> 
		
		</table> 
	</div> 
	 
	<!-- ************************************************************************ --> 
	<div id="negativeBalance_dlgEdit" class="easyui-dialog" title="Negative Balance Alert Email Eidt" style="width:600px;height:500px;padding:10px" 
	data-options=" 
	            iconCls: 'icon-edit', 
                modal: true,
	            closed: 'true', 
	            buttons: '#dlgEdit-buttons'"> 
	 
		 <div id="negativeBalanceEdit"> </div> 
	</div> 
	<div id="dlgEdit-toolbar" style="padding:2px 0"> 
		<table cellpadding="0" cellspacing="0" style="width:100%"> 
			<tr> 
				<td style="padding-left:2px"> 
				</td> 
			</tr> 
		</table> 
	</div> 
	
	<!-- ************************************************************************ --> 
    <!-- ************************************************************************ -->
    <div id="negativeBalance_dlgAdd" class="easyui-dialog" title="Negative Balance Alert Add" style="width:600px;height:500px;padding:10px"
            data-options="
                iconCls: 'icon-add',
                modal: true,
                closed: 'true', 
                buttons: '#dlgAdd-buttons'">
      <div id="negativeBalanceAdd"></div>
    </div>
    <div id="dlgAdd-toolbar" style="padding:2px 0">
        <table cellpadding="0" cellspacing="0" style="width:100%">
            <tr>
                <td style="padding-left:2px">
                </td>
            </tr>
        </table>
    </div>
   
</div>
	

		
		<!--main content end-->
<#include "/manage/foot.ftl">
