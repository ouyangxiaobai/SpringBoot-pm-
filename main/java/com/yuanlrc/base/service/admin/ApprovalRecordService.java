package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.App;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.ApprovalRecordDao;
import com.yuanlrc.base.entity.admin.ApprovalRecord;
import org.apache.poi.hssf.model.RecordStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 审核记录service
 * @author Administrator
 *
 */
@Service
public class ApprovalRecordService {

	@Autowired
	private ApprovalRecordDao approvalRecordDao;
	
	/**
	 * 根据记录id查询
	 * @param id
	 * @return
	 */
	public ApprovalRecord find(Long id){
		return approvalRecordDao.find(id);
	}

	
	/**
	 * 审批添加/编辑操作
	 * @param approvalRecord
	 * @return
	 */
	public ApprovalRecord save(ApprovalRecord approvalRecord){
		return approvalRecordDao.save(approvalRecord);
	}


	/**
	 * 审批记录分页list操作
	 * @param approvalRecord
	 * @param pageBean
	 * @return
	 */
	public PageBean<ApprovalRecord> findList(ApprovalRecord approvalRecord, PageBean<ApprovalRecord> pageBean) {
		Specification<ApprovalRecord> specification = new Specification<ApprovalRecord>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate=criteriaBuilder.like(root.get("biddingProject").get("title"),"%"+(approvalRecord.getBiddingProject() == null ? "" : approvalRecord.getBiddingProject().getTitle() +"%"));
				return predicate;
			}
		};
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
		Page<ApprovalRecord> findAll = approvalRecordDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

	/**
	 * 根据审批记录id删除
	 * @param id
	 */
	public void delete(Long id){
		approvalRecordDao.deleteById(id);
	}
	/**
	 * 返回审批记录总数
	 * @return
	 */
	public long total(){
		return approvalRecordDao.count();
	}

}
