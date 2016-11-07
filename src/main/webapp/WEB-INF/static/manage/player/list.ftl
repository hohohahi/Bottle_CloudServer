<#assign menu="player">
<#assign submenu="player_list">
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
										<th>手机号码</th>
										<th>名称</th>
										<th>状态</th>
										<th>余额</th>
                						<th>短信验证码</th>
              						</tr>
                                </thead>
                            	<tbody role="alert" aria-live="polite" aria-relevant="all">
                            		<#list playerList as e>
                            		<tr class="gradeA odd">
               							<td>
               								${e.phoneNumber}
               							</td>
               							<td>
               								${e.name}               								
               							</td>
               							<td>
               								${e.status}
               							</td>
               							<td>
               								${e.amount}
               							</td>
                            			<td>
                            				${e.smsCode}
                            			</td>                                    	
                                	</tr>
                                	</#list>
                               	</tbody>
                              </table>
                           </div>
                        </div>
                  </div>
              </section>
              <!-- page end-->
          </section>
		</section>
		<!--main content end-->
<#include "/manage/foot.ftl">
