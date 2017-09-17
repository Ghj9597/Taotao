package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

import cn.taotao.commom.pojo.EasyUITreeNode;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	//注入Mapper接口
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	public List<EasyUITreeNode> showContentCategory(Long parentId){
		//封装Example查询对象
		TbContentCategoryExample categoryExample = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		//传递criteria查询条件
		criteria.andParentIdEqualTo(parentId);
		//查询
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(categoryExample);
		//封装List<EasyUITreeNode> 返回值
		List<EasyUITreeNode> nodes=new ArrayList<EasyUITreeNode>();
		for (TbContentCategory contentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}
}
