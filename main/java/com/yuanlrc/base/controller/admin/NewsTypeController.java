package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.common.NewsType;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.common.NewsTypeService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台新闻分类管理控制器
 *
 */
@RequestMapping("/admin/news_type")
@Controller
public class NewsTypeController {

    @Autowired
    private NewsTypeService newsTypeService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 新闻分类列表
     *
     * @param model
     * @param newsType
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, NewsType newsType, PageBean<NewsType> pageBean) {
        model.addAttribute("title", "新闻分类列表");
        model.addAttribute("name",newsType.getName()==null?"":newsType.getName());
        model.addAttribute("pageBean", newsTypeService.findList(newsType, pageBean));
        return "admin/news/news_type_list";
    }

    /**
     * 新增用户页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "admin/news/news_type_add";
    }

    /**
     * 新闻分类添加表单提交处理
     *
     * @param newsType
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(NewsType newsType) {
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(newsType);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        if(newsTypeService.findByName(newsType.getName(),0l)){
            return Result.error(CodeMsg.ADMIN_NEWS_TYPE_NAME_EXIST_ERROR);
        }
        //到这说明一切符合条件，进行数据库新增
        if (newsTypeService.save(newsType) == null) {
            return Result.error(CodeMsg.ADMIN_NEWS_TYPE_ADD_ERROR);
        }
        operaterLogService.add("添加新闻分类 分类名称:" + newsType.getName());
        return Result.success(true);
    }

    /**
     * 新闻分类编辑页面
     * @param model
     * @return
     */
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        NewsType newsType = newsTypeService.find(id);
        model.addAttribute("newsType",newsType);
        return "admin/news/news_type_edit";
	}

	/**
     * 新闻分类编辑提交操作
     * @param newsType
     * @return
     */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> edit(NewsType newsType){
		//用统一验证实体方法验证是否合法
		CodeMsg validate = ValidateEntityUtil.validate(newsType);
		if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
			return Result.error(validate);
		}
		if(newsTypeService.findByName(newsType.getName(),newsType.getId())){
		    return Result.error(CodeMsg.ADMIN_NEWS_TYPE_NAME_EXIST_ERROR);
        }
		//到这说明一切符合条件，进行数据库保存
        NewsType newsType1 = newsTypeService.find(newsType.getId());
        //将提交的新闻分类和查询出来的newsType复制一遍,该方法会覆盖新字段内容
		BeanUtils.copyProperties(newsType, newsType1, "id","createTime","updateTime");
		if(newsTypeService.save(newsType1) == null){
			return Result.error(CodeMsg.ADMIN_NEWS_TYPE_EDIT_ERROR);
		}
		operaterLogService.add("编辑新闻分类 分类名称:" +newsType.getName());
		return Result.success(true);
	}

	/**
     * 删除新闻分类
     * @param id
     * @return
     */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
		try {
			newsTypeService.delete(id);
		} catch (Exception e) {
			return Result.error(CodeMsg.ADMIN_NEWS_TYPE_DELETE_ERROR);
		}
		operaterLogService.add("删除新闻分类 分类ID：" + id);
		return Result.success(true);
	}
}
