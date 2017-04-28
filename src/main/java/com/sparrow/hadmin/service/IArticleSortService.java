package com.sparrow.hadmin.service;

import com.sparrow.hadmin.entity.ArticleSort;
import com.sparrow.hadmin.service.support.IBaseService;
import com.sparrow.hadmin.vo.ArticleSortVo;

import java.util.List;

/**
 * <p>
 * 用户服务类
 * </p>
 *
 * @author 贤云
 * @since 2016-12-28
 */
public interface IArticleSortService extends IBaseService<ArticleSort, Integer> {

	/**
	 * 增加或者修改文章分类
	 * @param article
	 */
	void saveOrUpdate(ArticleSort article);

    void delete(Integer id);

    List<ArticleSort> findAllPage(int page,int size);

	List<ArticleSortVo> findByPid(String pid);
}
