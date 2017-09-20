package com.taotao.content.service;

import java.util.List;

import com.taotao.pojo.TbContent;

import cn.taotao.commom.pojo.EasyUIDateGridResource;
import cn.taotao.commom.pojo.TaotaoResult;
//创建内容管理Service
public interface ContentService {
	TaotaoResult InsertContent(TbContent content);
	List<TbContent> getContentList(Long cid);//根据分组id查询
	EasyUIDateGridResource showContent(Long categoryId,int page,int rows);
}
