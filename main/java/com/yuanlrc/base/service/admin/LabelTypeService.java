package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.LabelTypeDao;
import com.yuanlrc.base.entity.common.LabelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 标的物类型管理service
 * @author Administrator
 *
 */
@Service
public class LabelTypeService {

	@Autowired
	private LabelTypeDao labelTypeDao;
	
	/**
	 * 根据标的物类型id查询
	 * @param id
	 * @return
	 */
	public LabelType find(Long id){
		return labelTypeDao.find(id);
	}
	
	/**
	 * 标的物类型添加/编辑操作
	 * @param labelType
	 * @return
	 */
	public LabelType save(LabelType labelType){
		return labelTypeDao.save(labelType);
	}


	/**
	 * 标类型分页list操作
	 * @param labelType
	 * @param pageBean
	 * @return
	 */
	public PageBean<LabelType> findList(LabelType labelType, PageBean<LabelType> pageBean) {
		Specification<LabelType> specification = new Specification<LabelType>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate=criteriaBuilder.like(root.get("name"),"%"+(labelType.getName() == null ? "" : labelType.getName() +"%"));
				if(labelType.getStatus()!=0){
					Predicate equal1= criteriaBuilder.equal(root.get("status"), labelType.getStatus());
					criteriaBuilder.and(predicate,equal1);
				}
				return predicate;
			}
		};
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
		Page<LabelType> findAll = labelTypeDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 按照标的物类型id删除
	 * @param id
	 */
	public void delete(Long id){
		labelTypeDao.deleteById(id);
	}
	
	/**
	 * 返回标的物类型总数
	 * @return
	 */
	public long total(){
		return labelTypeDao.count();
	}
	/**
	 * 根据标类型名称查询
	 * @param name
	 * @return
	 */
	public Boolean findByName(String name,Long id){
		LabelType labelType = labelTypeDao.findByName(name);
		if(labelType!=null){
			if(!labelType.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据状态查询所有
	 * @param status
	 * @return
	 */
	public List<LabelType> findByStatus(int status){
		return labelTypeDao.findByStatus(status);
	}
}
