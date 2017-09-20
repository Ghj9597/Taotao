package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

import cn.taotao.commom.pojo.EasyUITreeNode;
import cn.taotao.commom.pojo.TaotaoResult;
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

	@Override
	public TaotaoResult addContentCategory(Long parentid, String name) {
		//封装category数据
		TbContentCategory category = new TbContentCategory();
		category.setName(name);
		category.setParentId(parentid);
		category.setIsParent(false);//新建节点肯定不是父节点
		category.setSortOrder(1);//排序方法默认为1
		category.setStatus(1);//设置状态为正常
		category.setCreated(new Date());
		category.setUpdated(new Date());
		tbContentCategoryMapper.insert(category);//插入节点
		//由于我们编写了<SELECT KEY>所以在插入之后ID内容封装入了节点中
		TbContentCategory category2 = tbContentCategoryMapper.selectByPrimaryKey(parentid);
		//获取到父节点,判断父节点的IsParent是否为true;
		Boolean isParent = category2.getIsParent();
		if(!isParent){
			category2.setIsParent(true);
		}
		//将父节点保存进入数据库
		tbContentCategoryMapper.updateByPrimaryKey(category2);
		return TaotaoResult.ok(category);//由于我们配置了SELECT KEY所以里边是有ID的
	}

	@Override
	public TaotaoResult contentDelete(Long ids) {
		//1.判断是否为父节点查询出来判断
		TbContentCategory node = tbContentCategoryMapper.selectByPrimaryKey(ids);
		if(node==null){
			return TaotaoResult.ok();
		}
		if(!node.getIsParent()){
			//不是父节点直接删除
			tbContentCategoryMapper.deleteByPrimaryKey(ids);
			//删除后判断父节点之下是否还有子节点;
			Long parentId = node.getParentId();//获取其父节点ID
			TbContentCategoryExample categoryExample = new TbContentCategoryExample();
			Criteria criteria = categoryExample.createCriteria();
			criteria.andParentIdEqualTo(parentId);
			List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(categoryExample);
			if(list.isEmpty()){
				//如果为空则代表其父类子节点
				TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
				parentNode.setIsParent(false);//设置为子节点
				//将修改后的保存进入数据库
				tbContentCategoryMapper.updateByPrimaryKey(parentNode);
			}
			//如果是父节点则不作修改
			return TaotaoResult.ok();
		}else{
			//如果是父节点,则获取所有其所有子节点
			TbContentCategoryExample categoryExample = new TbContentCategoryExample();
			Criteria criteria = categoryExample.createCriteria();
			criteria.andParentIdEqualTo(ids);
			List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(categoryExample);
			for (TbContentCategory tbContentCategory : list) {
				//遍历所有子节点
				this.contentDelete(tbContentCategory.getId());
			}
			this.contentDelete(ids);
			return TaotaoResult.ok();
		}
	}

	@Override
	public void update(Long id,String name) {
		TbContentCategory node = tbContentCategoryMapper.selectByPrimaryKey(id);
		node.setName(name);
		tbContentCategoryMapper.updateByPrimaryKey(node);
	}
}
