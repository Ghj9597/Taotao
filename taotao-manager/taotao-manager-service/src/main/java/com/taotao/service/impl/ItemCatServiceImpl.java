package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.EasyUITreeNode;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyUITreeNode> findNode(Long parentid)throws Exception{
		//构建查询对象;
		TbItemCatExample catExample = new TbItemCatExample();
		//添加查询条件
		Criteria criteria = catExample.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		//查询
		List<TbItemCat> list = tbItemCatMapper.selectByExample(catExample);
		//封装POJO类完成回显操作
		ArrayList<EasyUITreeNode> arrayList = new ArrayList<EasyUITreeNode>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbItemCat.getId());
			treeNode.setText(tbItemCat.getName());
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			arrayList.add(treeNode);
		}
		//返回值后进行发布服务
		return arrayList;
	}

}
