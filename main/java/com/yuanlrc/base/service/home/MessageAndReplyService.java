package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.MessageAndReplyDao;
import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.MessageAndReply;
import com.yuanlrc.base.entity.home.ProjectTimeVO;
import com.yuanlrc.base.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 留言回复Service
 */
@Service
public class MessageAndReplyService {

    @Autowired
    private MessageAndReplyDao messageAndReplyDao;

    /**
     * 保存
     * @param messageAndReply
     * @return
     */
    public MessageAndReply save(MessageAndReply messageAndReply){
        return messageAndReplyDao.save(messageAndReply);
    }

    /**
     * 根据用户id查询
     * @param homeUserId
     * @param pageBean
     * @return
     */
    public PageBean<MessageAndReply> findList(Long homeUserId,PageBean<MessageAndReply> pageBean,String title){
        Specification<MessageAndReply> specification = new Specification<MessageAndReply>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MessageAndReply> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate like = criteriaBuilder.like(root.get("biddingProject").get("title"), "%" + (title == null ? "" : title) + "%");
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                like = criteriaBuilder.and(like,predicate);
                return like;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<MessageAndReply> findAll = messageAndReplyDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据机构ID
     * @param orgId 机构ID
     * @param pageBean
     * @param msg 留言内容
     * @return
     */
    public PageBean<MessageAndReply> findListByOrgId(Long orgId,Long projectId, PageBean<MessageAndReply> pageBean, String msg)
    {
        Specification<MessageAndReply> specification = new Specification<MessageAndReply>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MessageAndReply> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("biddingProject"), projectId);

                Predicate like = criteriaBuilder.like(root.get("message"), msg == null ? "%%" : "%"+msg+"%");


                predicate = criteriaBuilder.and(predicate, like);
                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<MessageAndReply> findAll = messageAndReplyDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     *
     * @param id
     * @return
     */
    public MessageAndReply find(Long id)
    {
        return messageAndReplyDao.find(id);
    }
}
