package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.CommonProblemDao;
import com.yuanlrc.base.entity.admin.CommonProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 常见问题 service
 * @author zhong
 */
@Service
public class CommonProblemService {

    @Autowired
    private CommonProblemDao commonProblemDao;

    public CommonProblem find(Long id)
    {
        return commonProblemDao.find(id);
    }

    public CommonProblem save(CommonProblem entity)
    {
        return commonProblemDao.save(entity);
    }

    public void delete(Long id)
    {
        commonProblemDao.deleteById(id);
    }

    public List<CommonProblem> findAll()
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return commonProblemDao.findAll(sort);
    }

    public PageBean<CommonProblem> findList(CommonProblem commonProblem, PageBean<CommonProblem> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CommonProblem> example = Example.of(commonProblem, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<CommonProblem> findAll = commonProblemDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    /**
     * 根据问题名称查询是否存在
     * @param name
     * @return
     */
    public boolean findByName(String name, Long id){
        CommonProblem commonProblem = commonProblemDao.findByName(name);
        if(commonProblem != null){
            //表示名称存在，接下来判断是否是编辑用户的本身
            if(commonProblem.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

    public List<CommonProblem> findTop8ByOrderByIdDesc()
    {
        return commonProblemDao.findTop8ByOrderByIdDesc();
    }
}
