package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.RemindType;
import com.yuanlrc.base.dao.home.BiddingRemindDao;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingRemind;
import com.yuanlrc.base.util.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提醒Service
 */
@Service
public class BiddingRemindService {

    @Autowired
    private BiddingRemindDao biddingRemindDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public BiddingRemind find(Long id){
        return biddingRemindDao.find(id);
    }

    /**
     * 添加提醒
     * @param biddingRemind
     * @return
     */
    public BiddingRemind save(BiddingRemind biddingRemind){
        return biddingRemindDao.save(biddingRemind);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        biddingRemindDao.deleteById(id);
    }

    /**
     * 根据项目id和用户id查询
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    public BiddingRemind findByBiddingProjectIdAndHomeUserId(Long biddingProjectId,Long homeUserId){
        return biddingRemindDao.findByBiddingProjectIdAndHomeUserId(biddingProjectId,homeUserId);
    }

    /**
     * 根据项目id查询
     * @param biddingProjectId
     * @return
     */
    public List<BiddingRemind> findByBiddingProjectId(Long biddingProjectId){
        return biddingRemindDao.findByBiddingProjectId(biddingProjectId);
    }

    /**
     * 根据状态和项目id查询
     * @param biddingProjectId
     * @param status
     */
    public List<BiddingRemind> findByBiddingProjectIdAndStatus(Long biddingProjectId,int status){
        return biddingRemindDao.findByBiddingProjectIdAndStatus(biddingProjectId, status);

    }

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    public int updateStatus(Long id, Integer status) {
        return biddingRemindDao.updateStatus(id,status);
    }
}
