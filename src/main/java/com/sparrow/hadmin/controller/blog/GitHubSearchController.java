package com.sparrow.hadmin.controller.blog;

import com.sparrow.hadmin.api.github.MyComparator;
import com.sparrow.hadmin.controller.BaseController;
import com.sparrow.hadmin.service.IArticleService;
import com.sparrow.hadmin.service.IArticleSortService;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/github")
public class GitHubSearchController extends BaseController {

	private IArticleService articleService;
	private IArticleSortService articleSortService;

	@Autowired
	public GitHubSearchController(IArticleService articleService, IArticleSortService articleSortService) {
		this.articleService = articleService;
		this.articleSortService=articleSortService;
	}


	/**
	 * github-search
	 * @author:贤云
	 * @createDate:2017-03-28
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String githubSearchPage(Model model, HttpServletRequest request) {
		String user=request.getParameter("name");
		List<Repository> openSources=new ArrayList<>();
		try{
			if(!StringUtils.isEmpty(user)){
				RepositoryService service = new RepositoryService();
				for (Repository repo : service.getRepositories(user)){
					openSources.add(repo);
				}
				MyComparator myComparator=new MyComparator();
				Collections.sort(openSources,myComparator);
				model.addAttribute("openSources",openSources);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		model.addAttribute("total",openSources.size());
		return "html/blog/githubSearch";

	}
	/**
	 * github-search
	 * @author:郑云飞
	 * @createDate:2017-03-28
	 * @param model
	 * @return
	 */
	@GetMapping("/search/{user}")
	public String githubSearchPage(Model model, @PathVariable String user) {
		List<Repository> openSources=new ArrayList<>();
		try{
			if(!StringUtils.isEmpty(user)){
				RepositoryService service = new RepositoryService();
				for (Repository repo : service.getRepositories(user)){
					openSources.add(repo);
				}
				MyComparator myComparator=new MyComparator();
				Collections.sort(openSources,myComparator);
				model.addAttribute("openSources",openSources);//查询到项目集合
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		model.addAttribute("total",openSources.size());//查询到项目总数
		return "html/blog/githubSearch";

	}
}
