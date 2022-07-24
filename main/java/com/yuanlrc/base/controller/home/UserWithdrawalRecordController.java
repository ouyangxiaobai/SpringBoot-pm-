package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.UserBankCard;
import com.yuanlrc.base.entity.home.UserWithdrawalRecord;
import com.yuanlrc.base.service.home.UserBankCardService;
import com.yuanlrc.base.service.home.UserWithdrawalRecordService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping("/home/user_wr")
public class UserWithdrawalRecordController {

    @Autowired
    private UserWithdrawalRecordService userWithdrawalRecordService;

    @Autowired
    private UserBankCardService userBankCardService;

    /**
     * 提现申请
     * @param userWithdrawalRecord
     * @return
     */
    @RequestMapping(value = "/sendApply",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> sendApply(UserWithdrawalRecord userWithdrawalRecord){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }

        if(userWithdrawalRecord == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        if(userWithdrawalRecord.getMoney() == null){
            return Result.error(CodeMsg.MONEY_NULL_ERROR);
        }
        if(userWithdrawalRecord.getBankCardId() == null){
            return Result.error(CodeMsg.BANK_CARD_NULL_ERROR);
        }

        if(userWithdrawalRecord.getMoney().compareTo(BigDecimal.ZERO) <= 0){
            return Result.error(CodeMsg.MONET_MIN_ERROR);
        }
        if(userWithdrawalRecord.getMoney().compareTo(UserWithdrawalRecord.maxMoney) > 0){
            return Result.error(CodeMsg.MONET_MAX_ERROR);
        }

        UserBankCard userBankCard = userBankCardService.find(userWithdrawalRecord.getBankCardId());
        if(userBankCard == null){
            return Result.error(CodeMsg.BANK_CARD_ID_NULL_ERROR);
        }
        if(userBankCard.getHomeUser().getId().longValue() != loginedHomeUser.getId().longValue()){
            return Result.error(CodeMsg.BANK_CARD_HOME_USER_ERROR);
        }

        userWithdrawalRecord.setBankCard(userBankCard.getCardNumbers());
        userWithdrawalRecord.setHomeUser(loginedHomeUser);

        if(userWithdrawalRecordService.save(userWithdrawalRecord) == null){
            return Result.error(CodeMsg.WITHDRAWAL_RECORD_SAVE_ERROR);
        }

        return Result.success(true);
    }

}
