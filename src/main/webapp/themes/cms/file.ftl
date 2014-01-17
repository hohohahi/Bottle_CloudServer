<#include "header.ftl">
<style type="text/css">
.article_picture{
	width:80%;
}
 #slides {
	display:none;
 }
 
.slidesjs-pagination {
    float: right;
    list-style: none outside none;
    margin: 6px 0 0;
}
.slidesjs-pagination li {
    float: left;
    margin: 0 1px;
}
#slides a:link, #slides a:visited {
    color: #333333;
}
p, ul, ol {
    color: #777777;
    font-weight: 400;
}
.slidesjs-pagination li a.active, .slidesjs-pagination li a.active:hover {
    background-position: 0 -13px;
}

.slidesjs-pagination li a {
    background-image: url("${TEMPLATE_PATH}/assets/Slides-SlidesJS-3/examples/multiple/img/pagination.png");
    background-position: 0 0;
    display: block;
    float: left;
    height: 0;
    overflow: hidden;
    padding-top: 13px;
    width: 13px;
}
a:link, a:visited {
    color: #D22929;
    text-decoration: none;
}
.lof-slidecontent {
    border: 1px solid #F4F4F4;
    overflow: hidden;
    position: relative;
}
</style>
    <!--container start-->
    <div class="container">
        <div class="row">
            <!--blog start-->
            <div class="col-lg-9 ">
            <@shishuo_cms_file fileId="${fileId}">
                <div class="blog-item">
                    <div class="row">
                        <div class="col-lg-2 col-sm-2">
                            <div class="date-wrap">
                                <span class="date">${tag_file.createTime?string("dd")}</span>
                                <span class="month">${tag_file.createTime?string("MM")}月</div>
                            <div class="comnt-wrap">
                                <span class="comnt-ico">
                                    <i class="icon-comments"></i>
                                </span>
                                <span class="value">${tag_file.commentCount}</span>
                            </div>
                        </div>
                        <div class="col-lg-10 col-sm-10">
                        	<div id="slides" class="lof-slidecontent">
                           		<#list tag_file.photo as photo>
                            		<img src="${basePath}/upload/photo/${photo.fileId}_picture.jpg">
                            	</#list>
                            </div>
                            <h1><b>${tag_file.name}</b></h1>
                            <div class="author">
                               	作者：<a>${tag_file.admin.name}</a> | 浏览数：${tag_file.viewCount}
                            </div>
                            <p>
                            	${tag_file.title}
                            </p>
                            <p>
                            	${tag_file.description}
                            </p>
                            <hr>
                            <p>
                            	${tag_file.content}
                            </p>
                            <#list tag_file.file as fi>
                            <p>
                            	<a href=""title="${fi.size}b">${fi.name}</a>
                            </p>
                            </#list>
                            <@shishuo_cms_comment_page fileId="${fileId}" pageNum="${pageNum}" rows="10">
                            <#list tag_comment_page.list as comment>
                            <div class="media">
                             <#if  comment_index ==0>
                                <h3>评论</h3>
                                <hr>
                             </#if>
                                <a href="javascript:;" class="pull-left">
                                    <img alt="" src="${comment.faceUrl}" class="media-object">
                                </a>
                                <div class="media-body">
                                    <h4 class="media-heading">
                                        ${comment.name} <span>|</span>
                                        <span>${comment.createTime?string("yyyy-MM-dd")}</span>
                                    </h4>
                                    <p>
                                        ${comment.content}
                                    </p>
                                    <a href="javascript:;">回复</a>
                                    <hr>
                                    <!-- Nested media object -->
                                    <#list comment.childComment as child>
                                    <div class="media">
                                        <a href="javascript:;" class="pull-left">
                                            <img alt="" src="${child.faceUrl}" class="media-object">
                                        </a>
                                        <div class="media-body">
                                            <h4 class="media-heading">
                                                ${child.name} <span>|</span>
                                                <span>${child.createTime?string("yyyy-MM-dd")}</span>
                                            </h4>
                                            <p>
                                               ${child.content} 
                                            </p>
                                            <a href="javascript:;">回复</a>
                                        </div>
                                    </div>
                                    <!--end media-->
                                    <hr>
                                    </#list>
                                </div>
                            </div>
                            </#list>
                            </@shishuo_cms_comment_page>
                                <div class="post-comment">
	                                <h3 class="skills">发布评论</h3>
	                                <form role="form" class="form-horizontal" id="comment_form" action="${basePath}/comment/add.json" method="post">
	                                    <div class="form-group">
	                                        <div class="col-lg-4">
	                                            <input type="text" class="col-lg-12 form-control" alt="名字" placeholder="名字" name="name" required>
	                                        </div>
	                                        <div class="col-lg-4">
	                                            <input type="text" class="col-lg-12 form-control" alt="邮箱" placeholder="邮箱" name="email">
	                                        </div>
	                                        <input type="hidden" name="fileId" value="${fileId}">
                                        </div>
	                                    <div class="form-group">
	                                        <div class="col-lg-12">
	                                            <textarea class=" form-control" rows="8" placeholder="评语" name="content" required></textarea>
	                                        </div>
	                                    </div>
	                                    <p>
	                                        <button class="btn btn-danger pull-right" type="submit">发布</button>
	                                    </p>
	                                </form>
                                </div>  
                        </div>
                    </div>
                </div>
                </@shishuo_cms_file>
            </div>
            <div class="col-lg-3">
              <div class="blog-side-item">
              <#include "sidebar.ftl">
              </div>
            </div>
            <!--blog end-->
        </div>
    </div>
    <!--container end-->
    
 
   <script type="text/javascript">
	$(function(){
    	$("#slides").slidesjs({
    		pagination: {
          active: true,
          effect: "slide"
        }
    	});
		$("#comment_form").validate();
        $("#comment_form").ajaxForm({
			dataType:  'json',
			success:function(data){
				if(data.result){
					location.href="${basePath}/${ename}/"+${fileId}+".htm";
				}else{
					alert(data.msg);
				}
			}
	      });
	  
    });
   </script>
   <#include "footer.ftl">