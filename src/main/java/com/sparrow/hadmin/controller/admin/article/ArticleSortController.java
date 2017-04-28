package com.sparrow.hadmin.controller.admin.article;

import com.alibaba.fastjson.JSON;
import com.sparrow.hadmin.common.JsonResult;
import com.sparrow.hadmin.controller.BaseController;
import com.sparrow.hadmin.entity.ArticleSort;
import com.sparrow.hadmin.service.IArticleSortService;
import com.sparrow.hadmin.service.specification.SimpleSpecificationBuilder;
import com.sparrow.hadmin.service.specification.SpecificationOperator.Operator;
import com.sparrow.hadmin.vo.ArticleSortVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@despection  文章分类管理
 *@author 贤云
 *
 **/
@Controller
@RequestMapping("/admin/article/sort")
public class ArticleSortController extends BaseController {
	@Autowired
	private IArticleSortService articleSortService;
	/**
	 * @despection 初始化访问页面
	 * @author 贤云
	 * @qq 799078779
	 * @return
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/article/sort/index";
	}

	/**
	 *@despection  获取json数据集
	 * @author 贤云
	 * @qq 799078779
	 * @return
	 */
	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<ArticleSort> list() {
		Page<ArticleSort> page = null;
		try{
			SimpleSpecificationBuilder<ArticleSort> builder = new SimpleSpecificationBuilder<ArticleSort>();
			String searchText = request.getParameter("searchText");
			if(StringUtils.isNotBlank(searchText)){
				builder.add("nickName", Operator.likeAll.name(), searchText);
			}
			page=articleSortService.findAll(builder.generateSpecification(), getPageRequest());

		}catch (java.lang.Exception e){
			e.printStackTrace();
		}

		return page;
	}

	/**
	 *@despection  新增页面初始化
	 * @author 贤云
	 * @qq 799078779
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/article/sort/form";
	}

	/**
	 * 动态获取下拉树形结构的json数据
	 * 贤云
	 * @return
	 */
	@RequestMapping(value = "/tree/getJsonTree")
	@ResponseBody
	public String tree(){
		List<ArticleSort> parentList=articleSortService.findAll();
		String result=JSON.toJSONString(parentList);
		return result;
	}
	/**
	 * 动态获取下拉树形结构的json数据
	 * 贤云
	 * @return
	 */
	@RequestMapping(value = "/tree/getJsonTreeGrid")
	@ResponseBody
	public String tree(String page, String size){
		int pageNow=0;
		int pageSize=20;
		if(!org.springframework.util.StringUtils.isEmpty(page)){
			pageNow=Integer.parseInt(page);
		}
		if(!org.springframework.util.StringUtils.isEmpty(page)){
			pageSize=Integer.parseInt(size);
		}
		List<ArticleSortVo> list=new ArrayList<>();
		List<ArticleSort> parentList=articleSortService.findAllPage(pageNow,pageSize);
		for(int i=0;i<parentList.size();i++){
			ArticleSort articleSort=parentList.get(i);
			ArticleSortVo vo=ArticleSortVo.entityToBo(articleSort);
			list.add(vo);
		}
		setChildren(list);
		long total=articleSortService.count();
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("Rows",list);
		jsonMap.put("Total",total);
		String result=JSON.toJSONString(jsonMap);
		return result;
	}

	/**
	 * 递归下级
	 */
	public void setChildren(List<ArticleSortVo> list){
		ArticleSortVo obj =null;
		List<ArticleSortVo> childrenList=null ;
		for(ArticleSortVo bo:list){
			obj = new ArticleSortVo();
			obj.setPid(bo.getId());
			childrenList = articleSortService.findByPid(bo.getId()+"");
			if(!childrenList.isEmpty()){
				bo.setChildren(childrenList);
				setChildren(childrenList);
			}
		}
	}
	/**
	 *@despection  编辑页面初始化
	 * @author 贤云
	 * @qq 799078779
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		ArticleSort article = articleSortService.find(id);
		map.put("articleSort", article);
		return "admin/article/sort/form";
	}

	/**
	 *@despection  新增或者编辑文章保存
	 * @author 贤云
	 * @qq 799078779
	 * @param map
	 * @return
	 */
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(ArticleSort article, ModelMap map){
		try {
			articleSortService.saveOrUpdate(article);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	/**
	 *@despection  根据文章id删除文章信息
	 * @author 贤云
	 * @qq 799078779
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			articleSortService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

}
