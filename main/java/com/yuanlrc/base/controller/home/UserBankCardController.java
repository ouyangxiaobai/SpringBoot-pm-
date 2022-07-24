package com.yuanlrc.base.controller.home;

import com.alibaba.fastjson.JSONObject;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.UserBankCard;
import com.yuanlrc.base.service.home.UserBankCardService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 前台用户银行卡Controller
 */
@Controller
@RequestMapping("/home/userBankCard")
public class UserBankCardController {

    @Autowired
    private UserBankCardService userBankCardService;

    @GetMapping("/add")
    public String add(){
        return "home/bank_card/add";
    }

    /**
     * 添加银行卡
     * @param userBankCard
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(UserBankCard userBankCard){
        HomeUser homeUser = SessionUtil.getLoginedHomeUser();
        if(homeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        CodeMsg validate = ValidateEntityUtil.validate(userBankCard);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        //判断银行卡号和所属银行
        String cardDetail = StringUtil.getCardDetail(userBankCard.getCardNumbers());
        JSONObject jsonBank = JSONObject.parseObject(cardDetail);
        Boolean validated = (Boolean)jsonBank.get("validated");
        if(!validated){
            return Result.error(CodeMsg.BANK_CARD_ERROR);
        }
        String bank = (String)jsonBank.get("bank");
        if(!bank.equals(userBankCard.getBank())){
            return Result.error(CodeMsg.CARD_BANK_ERROR);
        }

        UserBankCard byCard = userBankCardService.findByCardNumbers(userBankCard.getCardNumbers());
        if(byCard != null){
            return Result.error(CodeMsg.CARD_HAS_ERROR);
        }

        if(!StringUtil.isMobile(userBankCard.getPhone())){
            return Result.error(CodeMsg.MOBILE_FORMAT_ERROR);
        }

        userBankCard.setHomeUser(homeUser);
        if(userBankCardService.save(userBankCard) == null){
            return Result.error(CodeMsg.CARD_ADD_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 删除银行卡
     * @param id
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result<Boolean> delete(Long id){

        try{
            userBankCardService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.CARD_DELETE_ERROR);
        }

        return Result.success(true);
    }
}
