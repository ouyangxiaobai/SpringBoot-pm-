package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.ProjectVo;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.admin.LabelTypeService;
import com.yuanlrc.base.service.admin.NewsService;
import com.yuanlrc.base.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * 前台首页控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/home/index")
public class HomeIndexController {

	@Autowired
	private LabelTypeService labelTypeService;

	@Autowired
	private BiddingProjectService biddingProjectService;

	@Autowired
	private NewsService newsService;

	/**
	 * 首页
	 * @param
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public String index(Model model){
		Date date = new Date();
		/*List<BiddingProject> top = biddingProjectService.findTop(ProjectStatus.INPUBLIC, ProjectStatus.BIDDING, ProjectStatus.SUCCESSFULBIDDING, ProjectStatus.ENDBIDDING, ProjectStatus.CLOSED);*/
		List<BiddingProject> top = biddingProjectService.findTop8();

		for (BiddingProject biddingProject : top) {
			biddingProjectService.status(biddingProject, date);
		}
		model.addAttribute("projectList",top);
		model.addAttribute("newsList",newsService.findTop());
		return "home/index/index";
	}
}
