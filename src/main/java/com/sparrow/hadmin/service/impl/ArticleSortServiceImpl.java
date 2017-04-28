package com.sparrow.hadmin.service.impl;

import com.sparrow.hadmin.dao.IArticleSortDao;
import com.sparrow.hadmin.dao.support.IBaseDao;
import com.sparrow.hadmin.entity.ArticleSort;
import com.sparrow.hadmin.service.IArticleSortService;
import com.sparrow.hadmin.service.support.impl.BaseServiceImpl;
import com.sparrow.hadmin.vo.ArticleSortVo;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文章服务实现类
 * </p>
 *
 * @author 贤云
 * @since 2016-12-28
 */
@Service
public class ArticleSortServiceImpl extends BaseServiceImpl<ArticleSort, Integer> implements IArticleSortService {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private IArticleSortDao articleSortDao;
	@Override
	public IBaseDao<ArticleSort, Integer> getBaseDao() {
		return this.articleSortDao;
	}

	@Override
	public void saveOrUpdate(ArticleSort article) {
		if(article.getId() != null){
			ArticleSort dbUser = find(article.getId());
			dbUser.setTitle(article.getTitle());
			dbUser.setDescription(article.getDescription());
			dbUser.setUpdateTime(new Date());
			update(dbUser);
		}else{
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			save(article);
		}
	}

	@Override
	public void delete(Integer id) {
		ArticleSort article = find(id);
		//Assert.state(!"admin".equals(article.getTitle()),"超级管理员用户不能删除");
		super.delete(id);
	}

	@Override
	public List<ArticleSort> findAllPage(int page,int size) {
		String sql="SELECT id,create_time as createTime,description,status,title,update_time as updateTime,pid,_label FROM tb_article_sort WHERE pid =999999 limit "+page+","+size+"";
		List<ArticleSort> list ;
				Query nativeQuery= entityManager
				.createNativeQuery(sql);
				nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(ArticleSort.class));
				list=nativeQuery.getResultList();

		return list;
	}

	@Override
	public List<ArticleSortVo> findByPid(String pid) {
		String sql="SELECT id,create_time as createTime,description,status,title,update_time as updateTime,pid,_label FROM tb_article_sort where pid="+pid;
		List<ArticleSortVo> list ;
		Query nativeQuery= entityManager
				.createNativeQuery(sql);
		nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(ArticleSortVo.class));
		list=nativeQuery.getResultList();
		return list;
	}


}
