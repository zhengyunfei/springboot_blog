package com.sparrow.hadmin.vo;

import com.sparrow.hadmin.entity.Article;
import com.sparrow.hadmin.entity.ArticleSort;
import com.sparrow.hadmin.entity.support.BaseEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章分类表
 * </p>
 *
 * @author 贤云
 * @since 2016-12-28
 */
public class ArticleSortVo extends BaseEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = -1894163644285296223L;

	private Integer id;

	/**
	 * 分类名称
	 */
	private String title;

	/**
	 * 分类描述
	 */
	private String description;

	/**
	 * 状态,0：正常；1：删除
	 */
	private Integer status;


	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 父节点id
	 */
	private Integer pid;
	/**
	 * 节点深度
	 */
	private Integer _label;
	/**
	 * 是否有子节点
	 */
	private boolean isLeaf;
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer get_label() {
		return _label;
	}

	public void set_label(Integer _label) {
		this._label = _label;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}
    private List<ArticleSortVo> children;

	public List<ArticleSortVo> getChildren() {
		return children;
	}

	public void setChildren(List<ArticleSortVo> children) {
		this.children = children;
	}

	private List<Article> articleList=new ArrayList<>();
	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public static ArticleSortVo entityToBo(ArticleSort entity){
		ArticleSortVo vo=new ArticleSortVo();
		vo.setId(entity.getId());
		vo.setTitle(entity.getTitle());
		vo.setDescription(entity.getDescription());
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setCreateTime(simpleDateFormat.format(entity.getCreateTime()));
		vo.setUpdateTime(simpleDateFormat.format(entity.getUpdateTime()));
		vo.setStatus(entity.getStatus());
		vo.setPid(entity.getPid());
		vo.set_label(entity.get_label());
		return vo;
	}
}
