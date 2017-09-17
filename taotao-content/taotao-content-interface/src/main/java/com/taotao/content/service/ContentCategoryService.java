package com.taotao.content.service;

import java.util.List;

import cn.taotao.commom.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	List<EasyUITreeNode> showContentCategory(Long parentId);
}
