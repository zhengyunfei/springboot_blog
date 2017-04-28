<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>文章分类列表</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx!}/hadmin/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx!}/hadmin/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx!}/hadmin/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx!}/hadmin/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/hadmin/css/style.css?v=${version}" rel="stylesheet">
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox ">
                    <div class="ibox-title">
                        <h5>文章分类管理</h5>
                    </div>
                    <div class="ibox-content">
                        <p>
                        	<@shiro.hasPermission name="system:article:add">
                        		<button class="btn btn-success " type="button" onclick="add();">
									<i class="fa fa-plus"></i>&nbsp;添加
								</button>
                        	</@shiro.hasPermission>
                        </p>
                        <hr>
                        <div class="row row-lg">
		                    <div class="col-sm-12">
		                        <!-- Example Card View -->
		                        <div class="example-wrap">
		                            <#--<div class="example">
		                            	<table id="table_list"></table>
		                            </div>-->
										<div id="treeGrid"></div>
                                   <#--     <div id="treegrid1" class="mini-pagertree" style="width:700px;height:280px;"
                                             url="${ctx!}/admin/article/sort/tree/getJsonTreeGrid" showTreeIcon="true"
                                             treeColumn="title" idField="id" parentField="pid" resultAsTree="true" pageSize="20"
                                             allowResize="true" expandOnLoad="true"
                                        >-->

                                        </div>


									</div>
		                        <!-- End Example Card View -->
		                    </div>
	                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 全局js -->
	<#include "/admin/common/common.ftl">
    <link href="http://www.ligerui.com/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="http://www.ligerui.com/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="http://www.ligerui.com/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <!-- Page-Level Scripts -->
    <script>
        var manager;
        function  initGrid(data) {
             manager = $("#treeGrid").ligerGrid({
                        columns: [
                            { display: '标示', name: 'id', width: 250, type: 'int', align: 'left' },
                            { display: '分类名称', name: 'title', width: 250, align: 'left' },
                            { display: '描述', name: 'description', width: 250, align: 'left' }
                            ,{
                                display: "创建时间",
                                name: "createTime"
                            }, {
                                display: "操作",
                                name: "empty",
                                render: function ( row) {
                                    var operateHtml = '<@shiro.hasPermission name="system:article:edit"> <button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+ row.id+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;</@shiro.hasPermission>';
                                    operateHtml = operateHtml + '<@shiro.hasPermission name="system:article:deleteBatch"><button class="btn btn-danger btn-xs" type="button" onclick="del(\''+ row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button> &nbsp;</@shiro.hasPermission>';
                                    return operateHtml;
                                }
                            }
                        ], width: '100%', pageSizeOptions: [5, 10, 15, 20], height: '97%',
                        data: data, tree: { columnName: 'id' },pageSize:20,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize",alternatingRow: false
                    }
            );
        }

        $(document).ready(function () {
            var url="${ctx!}/admin/article/sort/tree/getJsonTreeGrid";
           getData(url);


        });
		function getData(url){
            var data;
            $.ajax({
				url:url,
				dataType:'json',
				method:'post',
				success:function(res){
                    data=res;
                    console.log(data);
                    initGrid(eval("("+data+")"));
				},error:function(){

				}
			});
			return data;
		}
       /* $(document).ready(function () {
        	//初始化表格,动态从服务器加载数据
			$("#table_list").bootstrapTable({
			    //使用get请求到服务器获取数据
			    method: "POST",
			    //必须设置，不然request.getParameter获取不到请求参数
			    contentType: "application/x-www-form-urlencoded",
			    //获取数据的Servlet地址
			    url: "${ctx!}/admin/article/sort/list",
			    //表格显示条纹
			    striped: true,
			    //启动分页
			    pagination: true,
			    //每页显示的记录数
			    pageSize: 10,
			    //当前第几页
			    pageNumber: 1,
			    //记录数可选列表
			    pageList: [5, 10, 15, 20, 25,30,50,100,200],
			    //是否启用查询
			    search: true,
			    //是否启用详细信息视图
			    detailView:true,
			    detailFormatter:detailFormatter,
			    //表示服务端请求
			    sidePagination: "server",
			    //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
			    //设置为limit可以获取limit, offset, search, sort, order
			    queryParamsType: "undefined",
			    //json数据解析
			    responseHandler: function(res) {
			        return {
			            "rows": res.content,
			            "total": res.totalElements
			        };
			    },
			    //数据列
			    columns: [{
			        title: "ID",
			        field: "id",
			        sortable: true
			    },{
			        title: "名称",
			        field: "title"
			    },
                    {
                        title: "描述",
                        field: "description"
                    },{
			        title: "创建时间",
			        field: "createTime",
			        sortable: true
			    },{
			        title: "操作",
			        field: "empty",
                    formatter: function (value, row, index) {
                    	var operateHtml = '<@shiro.hasPermission name="system:article:edit"> <button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+row.id+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;</@shiro.hasPermission>';
                    	operateHtml = operateHtml + '<@shiro.hasPermission name="system:article:deleteBatch"><button class="btn btn-danger btn-xs" type="button" onclick="del(\''+row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button> &nbsp;</@shiro.hasPermission>';
                        return operateHtml;
                    }
			    }]
			});
        });*/

        function edit(id){
        	layer.open({
        	      type: 2,
        	      title: '分类修改',
        	      shadeClose: true,
        	      shade: 0,
				  maxmin: false,
        	      area: ['893px', '600px'],
        	      content: '${ctx!}/admin/article/sort/edit/' + id,
        	      end: function(index){
        	    	  $('#table_list').bootstrapTable("refresh");
       	    	  }
        	    });
        }
        function add(){
        	layer.open({
        	      type: 2,
        	      title: '分类添加',
        	      shadeClose: true,
        	      shade: false,
				  maxmin: false, //开启最大化最小化按钮
        	      area: ['893px', '600px'],
        	      content: '${ctx!}/admin/article/sort/add',
        	      end: function(index){
        	    	  $('#table_list').bootstrapTable("refresh");
       	    	  }
        	    });
        }

        function del(id){
        	layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
        		$.ajax({
    	    		   type: "POST",
    	    		   dataType: "json",
    	    		   url: "${ctx!}/admin/article/sort/delete/" + id,
    	    		   success: function(msg){
	 	   	    			layer.msg(msg.message, {time: 2000},function(){
	 	   	    				$('#table_list').bootstrapTable("refresh");
	 	   	    				layer.close(index);
	 	   					});
    	    		   }
    	    	});
       		});
        }

        function detailFormatter(index, row) {
	        var html = [];
	        html.push('<p><b>描述:</b> ' + row.description + '</p>');
	        return html.join('');
	    }
    </script>




</body>

</html>
