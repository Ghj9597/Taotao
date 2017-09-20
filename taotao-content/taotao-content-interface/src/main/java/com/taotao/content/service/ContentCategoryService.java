package com.taotao.content.service;

import java.util.List;

import cn.taotao.commom.pojo.EasyUITreeNode;
import cn.taotao.commom.pojo.TaotaoResult;

public interface ContentCategoryService {
	List<EasyUITreeNode> showContentCategory(Long parentId);
	TaotaoResult addContentCategory(Long parentid,String name);
}
