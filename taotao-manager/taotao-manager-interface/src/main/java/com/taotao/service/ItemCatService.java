package com.taotao.service;

import java.util.List;

import cn.taotao.commom.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> findNode(Long parentid)throws Exception;
}
