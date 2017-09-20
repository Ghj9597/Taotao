<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<ul id="contentCategory" class="easyui-tree">
	</ul>
</div>
<div>
	<a class="easyui-linkbutton" onclick="importItem()">一键导入商品</a>
</div>
<script type="text/javascript">
	function importItem() {
		$.post("/index/import", null, function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '一键导入商品成功!');
			} else {
				$.messager.alert('提示', '一键导入商品失败!');
			}
		});
	}
</script>