package com.yuanlrc.base.bean;
/**
 * 错误码统一处理类，所有的错误码统一定义在这里
 * @author Administrator
 *
 */
public class CodeMsg {

    private int code;//错误码
	
	private String msg;//错误信息
	
	/**
	 * 构造函数私有化即单例模式
	 * @param code
	 * @param msg
	 */
	private CodeMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//通用错误码定义
	//处理成功消息码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	//非法数据错误码
	public static CodeMsg DATA_ERROR = new CodeMsg(-1, "非法数据！");
	public static CodeMsg CPACHA_EMPTY = new CodeMsg(-2, "验证码不能为空！");
	public static CodeMsg VALIDATE_ENTITY_ERROR = new CodeMsg(-3, "");
	public static CodeMsg SESSION_EXPIRED = new CodeMsg(-4, "会话已失效，请刷新页面重试！");
	public static CodeMsg CPACHA_ERROR = new CodeMsg(-5, "验证码错误！");
	public static CodeMsg USER_SESSION_EXPIRED = new CodeMsg(-6, "还未登录或会话失效，请重新登录！");
	public static CodeMsg UPLOAD_PHOTO_SUFFIX_ERROR = new CodeMsg(-7, "图片格式不正确！");
	public static CodeMsg UPLOAD_PHOTO_ERROR = new CodeMsg(-8, "图片上传错误！");
	public static CodeMsg SAVE_ERROR = new CodeMsg(-11, "保存失败，请联系管理员！");
	public static CodeMsg ORDER_SN_ERROR = new CodeMsg(-12, "订单编号错误！");
	public static CodeMsg PHONE_ERROR = new CodeMsg(-13, "手机号错误！");
	public static CodeMsg ORDER_AUTH_ERROR = new CodeMsg(-14, "\u8ba2\u5355\u9a8c\u8bc1\u5931\u8d25\uff0c\u8ba2\u5355\u7f16\u53f7\u6216\u624b\u673a\u53f7\u8f93\u5165\u6709\u8bef\u6216\u8005\u53ef\u80fd\u4f60\u8d2d\u4e70\u7684\u662f\u76d7\u7248\uff0c\u8bf7\u8054\u7cfb\u3010\u733f\u6765\u5165\u6b64\u3011\u5ba2\u670d\uff01");
	
	//后台管理类错误码
	//用户管理类错误
	public static CodeMsg ADMIN_USERNAME_EMPTY = new CodeMsg(-2000, "用户名不能为空！");
	public static CodeMsg ADMIN_PASSWORD_EMPTY = new CodeMsg(-2001, "密码不能为空！");
	public static CodeMsg ADMIN_NO_RIGHT = new CodeMsg(-2002, "您所属的角色没有该权限！");
	
	//登录类错误码
	public static CodeMsg ADMIN_USERNAME_NO_EXIST = new CodeMsg(-3000, "该用户名不存在！");
	public static CodeMsg ADMIN_PASSWORD_ERROR = new CodeMsg(-3001, "密码错误！");
	public static CodeMsg ADMIN_USER_UNABLE = new CodeMsg(-3002, "该用户已被冻结，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_UNABLE = new CodeMsg(-3003, "该用户所属角色状态不可用，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_AUTHORITES_EMPTY = new CodeMsg(-3004, "该用户所属角色无可用权限，请联系管理员！");
	
	//后台菜单管理类错误码
	public static CodeMsg ADMIN_MENU_ADD_ERROR = new CodeMsg(-4000, "菜单添加失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_EDIT_ERROR = new CodeMsg(-4001, "菜单编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_ID_EMPTY = new CodeMsg(-4002, "菜单ID不能为空！");
	public static CodeMsg ADMIN_MENU_ID_ERROR = new CodeMsg(-4003, "菜单ID错误！");
	public static CodeMsg ADMIN_MENU_DELETE_ERROR = new CodeMsg(-4004, "改菜单下有子菜单，不允许删除！");
	//后台角色管理类错误码
	public static CodeMsg ADMIN_ROLE_ADD_ERROR = new CodeMsg(-5000, "角色添加失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_NO_EXIST = new CodeMsg(-5001, "该角色不存在！");
	public static CodeMsg ADMIN_ROLE_EDIT_ERROR = new CodeMsg(-5002, "角色编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_DELETE_ERROR = new CodeMsg(-5003, "该角色下存在用户信息，不可删除！");
	//后台用户管理类错误码
	public static CodeMsg ADMIN_USER_ROLE_EMPTY = new CodeMsg(-6000, "请选择用户所属角色！");
	public static CodeMsg ADMIN_USERNAME_EXIST = new CodeMsg(-6001, "该用户名已存在，请换一个试试！");
	public static CodeMsg ADMIN_USE_ADD_ERROR = new CodeMsg(-6002, "用户添加失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_NO_EXIST = new CodeMsg(-6003, "用户不存在！");
	public static CodeMsg ADMIN_USE_EDIT_ERROR = new CodeMsg(-6004, "用户编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_DELETE_ERROR = new CodeMsg(-6005, "该用户存在关联数据，不允许删除！");
	
	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_USER_UPDATE_PWD_ERROR = new CodeMsg(-7000, "旧密码错误！");
	public static CodeMsg ADMIN_USER_UPDATE_PWD_EMPTY = new CodeMsg(-7001, "新密码不能为空！");
	
	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_DATABASE_BACKUP_NO_EXIST = new CodeMsg(-8000, "备份记录不存在！");

	//后台标类型错误码
	public static CodeMsg ADMIN_LABEL_TYPE_ADD_ERROR=new CodeMsg(-20000,"标的物类型添加失败!");
	public static CodeMsg ADMIN_LABEL_TYPE_NAME_EXIST=new CodeMsg(-20001,"标的物类型名称已存在,请重新填写!");
	public static CodeMsg ADMIN_LABEL_TYPE_NOT_EXIST=new CodeMsg(-20002,"标的物类型不存在！");
	public static CodeMsg ADMIN_LABEL_TYPE_EDIT_ERROR=new CodeMsg(-20003,"标的物类型编辑失败!");
	public static CodeMsg ADMIN_LABEL_TYPE_DELETE_ERROR=new CodeMsg(-20004,"删除失败,该类型下存在关联数据!");
	public static CodeMsg ADMIN_LABEL_TYPE_BE_USED=new CodeMsg(-20005,"该类型已被使用，无法修改!");

	//后台竞拍错误码
	public static CodeMsg ADMIN_BIDDING_PROJECT_SIGNUP_DATE_ERROR=new CodeMsg(-21000,"报名开始日期不能大于结束日期");
	public static CodeMsg ADMIN_BIDDING_PROJECT_BIDDING_DATE_ERROR=new CodeMsg(-21001,"竞拍开始日期不能大于结束日期");
	public static CodeMsg ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR=new CodeMsg(-21002,"竞拍不存在,请联系管理员！");
	public static CodeMsg ADMIN_BIDDING_PROJECT_EDIT_ERROR=new CodeMsg(-21003,"竞拍编辑失败!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_ADD_ERROR=new CodeMsg(-21004,"竞拍添加失败!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_DELETE_ERROR=new CodeMsg(-21005,"竞拍删除失败，竞拍下有关联数据!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_RELEASE_ERROR=new CodeMsg(-21006,"竞拍发布失败,请联系管理员!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_EXAMINE_ERROR=new CodeMsg(-21007,"竞拍审核失败,请联系管理员!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_LOADING_ERROR=new CodeMsg(-21008,"竞拍上架失败!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_UNLOADING_ERROR=new CodeMsg(-21009,"竞拍下架失败!");
	public static CodeMsg ADMIN_BIDDING_PROJECT_STATUS_ERROR=new CodeMsg(-21010,"该状态不能下架");
	public static CodeMsg ADMIN_BIDDING_PROJECT_APPLY_ERROR=new CodeMsg(-21011,"该项目已被报名,不可下架");
	public static CodeMsg ADMIN_BIDDING_PROJECT_SIGNUP_TIME_ERROR=new CodeMsg(-21012,"报名时间不能小于当前时间");
	public static CodeMsg ADMIN_BIDDING_PROJECT_SIN_TIME_ERROR=new CodeMsg(-21013,"报名时间已过，不能给与通过");
	public static CodeMsg ADMIN_BIDDING_PROJECT_STAUTS_ERROR=new CodeMsg(-21014,"该项目状态不可编辑");
	public static CodeMsg ADMIN_BIDDING_PROJECT_BOND_PAY_ERROR=new CodeMsg(-21015,"当前余额不足以支付保证金,请去个人用户充值");

	//后台审批记录错误码
	public static CodeMsg ADMIN_APPROVAL_RECORD_ADD_ERROR=new CodeMsg(-22001,"审批记录添加失败");
	public static CodeMsg ADMIN_APPROVAL_RECORD_DELETE_ERROR=new CodeMsg(-22002,"审批记录删除失败");

	//登录错误
	public static CodeMsg PASSWORD_MIN_LENGTH_ERROR = new CodeMsg(-8001, "密码最少为4位");
	public static CodeMsg PASSWORD_MAX_LENGTH_ERROR = new CodeMsg(-8002, "密码最多为18位");
	public static CodeMsg USERNAME_MIN_LENGTH_ERROR = new CodeMsg(-8003, "账号最少为4位");
	public static CodeMsg USERNAME_MAX_LENGTH_ERROR = new CodeMsg(-8004, "账号最多为18位");

	/**
	 * @author zhong
	 */

	public static CodeMsg ADMIN_COMMON_PROBLEM_ADD_ERROR = new CodeMsg(-60000, "常见问题添加失败");
	public static CodeMsg ADMIN_COMMON_PROBLEM_DELETE_ERROR = new CodeMsg(-60001, "常见问题删除失败");
	public static CodeMsg ADMIN_COMMON_PROBLEM_EMPTY_ERROR = new CodeMsg(-60002, "问题名称重复了");
	public static CodeMsg ADMIN_COMMON_PROBLEM_EDIT_ERROR = new CodeMsg(-60004, "常见问题编辑失败");


	//机构
	public static CodeMsg ADMIN_ORGANIZATION_DELETE_ERROR = new CodeMsg(-61000, "机构删除失败");
	public static CodeMsg ADMIN_ORGANIZATION_EDIT_ERROR = new CodeMsg(-61001, "机构编辑失败");
	public static CodeMsg ADMIN_EDIT_AUDIT_STATUS_ERROR = new CodeMsg(-61002, "该机构还未通过，无法冻结改账号");
	public static CodeMsg ADMIN_EDIT_AUDIT_STATUS_ERROR2 = new CodeMsg(-61003, "该机构不在冻结状态，无法设置为通过状态");
	public static CodeMsg ADMIN_ORGANIZATION_EARNESTMONEY_STATUS_ERROR=new CodeMsg(-61004,"请支付保证金后,再选择上架项目");
	public static CodeMsg ADMIN_ORGANIZATION_AUDITSTATUS_ERROR=new CodeMsg(-61005,"机构还未通过审核,不能上架项目");
	//机构
	public static CodeMsg ADMIN_EMAIL_NOT_FOUND_ERROR = new CodeMsg(-70000, "该邮箱还未注册");
	public static CodeMsg ADMIN_PASSWORD_NULL_ERROR = new CodeMsg(-70001, "请输入密码");
	public static CodeMsg ADMIN_EMAIL_NULL_ERROR = new CodeMsg(-70002, "请输入邮箱");
	public static CodeMsg ADMIN_CODE_NULL_ERROR = new CodeMsg(-70003, "请输入验证码");
	public static CodeMsg ADMIN_ATTRIBUTE_NOT_SEND = new CodeMsg(-70004, "请发送验证码");
	public static CodeMsg ADMIN_EMAIL_UPDATE_ERROR = new CodeMsg(-70005, "邮箱不一致");
	public static CodeMsg ADMIN_MODIFICATION_PASSWORD_ERROR = new CodeMsg(-70006, "修改密码失败");
	public static CodeMsg ADMIN_ORGANIZATION_INFO_ERROR = new CodeMsg(-70007, "机构信息修改失败");
	public static CodeMsg ADMIN_BALANCE_ERROR = new CodeMsg(-70008, "余额不足");
	public static CodeMsg ADMIN_PAY_CASH_PLEDGE_ERROR = new CodeMsg(-70009, "支付失败");
	public static CodeMsg SESSION_CODE_OVER_TIME_ERROR = new CodeMsg(-70010, "验证码已过期");
	public static CodeMsg ADMIN_REGISTER_EMAIL_ERROR = new CodeMsg(-70011, "邮箱不一致");
	public static CodeMsg ADMIN_REGISTER_ERROR = new CodeMsg(-70012, "注册失败");
	public static CodeMsg ADMIN_AUDIT_EDIT_ERROR = new CodeMsg(-70013, "机构信息修改失败");
	public static CodeMsg ADMIN_PASSWORD_LENGTH_ERROR = new CodeMsg(-70014, "新密码长度必须4-32位");



	//
	public static CodeMsg ADMIN_SUBMIT_AUDIT_ERROR = new CodeMsg(-70020, "提交审核失败，你已经提交过了");
	public static CodeMsg ADMIN_SUBMIT_AUDIT_ERROR2 = new CodeMsg(-70021, "提交审核失败");

	public static CodeMsg ADMIN_APPROVAL_ERROR = new CodeMsg(-70022, "审核失败");
	public static CodeMsg ADMIN_APPROVAL_MSG_LENGTH_ERROR = new CodeMsg(-70022, "未通过原因必须小于50字");

	public static CodeMsg ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR = new CodeMsg(-73000, "你的账号管理员还未通过");

	//机构密码
	public static CodeMsg ADMIN_PAY_PWD_NULL_ERROR = new CodeMsg(-74000, "支付密码不能为空");
	public static CodeMsg ADMIN_PAY_PWD_UPDATE_ERROR = new CodeMsg(-74001, "支付密码修改失败");

	//机构银行卡管理
	public static CodeMsg ADMIN_ORG_BANK_CARD_DELETE_ERROR = new CodeMsg(-74100, "银行卡信息删除失败");
	public static CodeMsg ADMIN_ORG_BANK_CARD_ADD_ERROR = new CodeMsg(-74101, "银行卡添加失败");
	public static CodeMsg ADMIN_ORG_BANK_CARD_REPETITION_ERROR = new CodeMsg(-74102, "银行卡号码重复");

	//充值
	public static CodeMsg ADMIN_ORG_ALIPAY_NUMBER_ADD_ERROR = new CodeMsg(-74200, "你充值的金额不能小于1");
	public static CodeMsg ADMIN_ORG_ALIPAY_ADD_ERROR = new CodeMsg(-74201, "订单生成失败");
	public static CodeMsg ALIPAY_NUMBER_MORE_ERROR = new CodeMsg(-74202, "你充值的金额不能大于100000");


	public static CodeMsg ADMIN_ORGANIZATION_NOT_PAY_MONEY = new CodeMsg(-75000, "未支付押金");
	public static CodeMsg ADMIN_ORG_WR_ADD_MONEY_ERROR = new CodeMsg(-75001, "你输入的金额是负数");
	public static CodeMsg ADMIN_ORG_BALANCE_ERROR = new CodeMsg(-75002, "余额不足");
	public static CodeMsg ADMIN_BANKCARDINFO_NOT_FOUND_ERROR = new CodeMsg(-75003, "请选择银行卡");
	public static CodeMsg ADMIN_ORG_WR_ADD_ERROR = new CodeMsg(-75004, "提交申请失败");
	public static CodeMsg ADMIN_ORG_PAY_ERROR = new CodeMsg(-75005, "你已经支付过了");

	public static CodeMsg ADMIN_ORGANIZATION_NOT_FOUND_ERROR = new CodeMsg(-75006, "机构不存在");


	//新闻标签
	public static CodeMsg ADMIN_NEWS_TYPE_ADD_ERROR=new CodeMsg(-75010,"新闻分类添加失败!");
	public static CodeMsg ADMIN_NEWS_TYPE_NAME_EXIST_ERROR=new CodeMsg(-75011,"新闻分类名称已存在,请重新填写!");
	public static CodeMsg ADMIN_NEWS_TYPE_EDIT_ERROR=new CodeMsg(-75012,"新闻分类编辑失败!");
	public static CodeMsg ADMIN_NEWS_TYPE_DELETE_ERROR=new CodeMsg(-75013,"该分类下有关联数据不可删除!");
	public static CodeMsg ADMIN_NEWS_NOT_SELECT_ERROR = new CodeMsg(-75014, "请选择新闻分类");
	public static CodeMsg ADMIN_NEWS_ADD_ERROR = new CodeMsg(-75015, "新闻添加失败");

	//通知
	public static CodeMsg ADMIN_INFORM_DELETE_ERROR = new CodeMsg(-76000, "通知删除失败");
	public static CodeMsg ADMIN_INFORM_ADD_ERROR = new CodeMsg(-76001, "通知添加失败");
	public static CodeMsg ADMIN_INFORM_CAPTION_ERROR = new CodeMsg(-76002, "通知标题重复");

	//收藏
	public static CodeMsg HOME_COLLECT_ADD_ERROR = new CodeMsg(-76010, "添加收藏失败");
	public static CodeMsg HOME_COLLECT_EXIST_ADD_ERROR = new CodeMsg(-76011, "你已经收藏过了");
	public static CodeMsg HOME_COLLECT_DELETE_ERROR = new CodeMsg(-76012, "取消收藏失败");
	public static CodeMsg HOME_COLLECT_NOT_EXIST_DELETE_ERROR = new CodeMsg(-76013, "这不是你的收藏");
	public static CodeMsg ADMIN_REPLY_ERROR = new CodeMsg(-76014, "回复失败");
	public static CodeMsg ADMIN_REPLY_NULL_ERROR = new CodeMsg(-76015, "回复内容不能为空");
	public static CodeMsg ADMIN_REPLY_LENGTH_ERROR = new CodeMsg(-76016, "你的回复内容必须在4~100字之间");
	public static CodeMsg ADMIN_BIDDING_PROJECT_PROJECT_STATUS_ERROR = new CodeMsg(-76017, "你的状态不在竞价成功阶段");
	public static CodeMsg ADMIN_BIDDING_PROJECT_SET_OVERDUE_ERROR = new CodeMsg(-76018, "设置项目逾期失败");
	public static CodeMsg ADMIN_SET_OVERDUE_DATE_TIME_ERROR = new CodeMsg(-76019, "请在竞拍结束是七天之后才可以设置逾期");
	public static CodeMsg ADMIN_ORGANIZATION_NAME_ERROR = new CodeMsg(-76020, "机构名称重复");

	////////////////////////////////////////////////////////////////////////////////////

	//邮件发送错误码
	public static CodeMsg COMMON_EMAIL_FORMAET_ERROR = new CodeMsg(-10000,"邮箱格式错误");

	//前台用户注册错误码
	public static CodeMsg CODE_NULL_ERROR = new CodeMsg(-10001,"注册码不能为空!");
	public static CodeMsg EMAIL_CODE_NOT_SEND = new CodeMsg(-10002,"还未发送邮箱验证码!");
	public static CodeMsg CODE_TIME_OVER_ERROR = new CodeMsg(-10003,"验证码已失效!");
	public static CodeMsg CODE_ERROR = new CodeMsg(-10004,"验证码错误!");
	public static CodeMsg EMAIL_ERROR = new CodeMsg(-10005,"当前邮箱错误!");
	public static CodeMsg MOBILE_FORMAT_ERROR = new CodeMsg(-10006,"手机号格式错误!");
	public static CodeMsg EMAIL_HAS_REGISTER = new CodeMsg(-10007,"邮箱已被注册!");
	public static CodeMsg MOBILE_HAS_REGISTER = new CodeMsg(-10008,"手机号已被注册!");
	public static CodeMsg ID_NUMBER_FORMAT_ERROR = new CodeMsg(-10009,"身份证号格式错误!");
	public static CodeMsg ID_NUMBER_HAS_REGISTER = new CodeMsg(-10010,"身份证号已被注册!");
	public static CodeMsg HOME_USER_SAVE_ERROR = new CodeMsg(-10011,"注册失败!");


	public static CodeMsg COMMON_PHONE_FORMAET_ERROR = new CodeMsg(-90000, "手机号格式错误");
	public static CodeMsg COMMON_EMAIL_EXSITER_ERROR = new CodeMsg(-90001, "邮箱已经存在了");
	public static CodeMsg COMMON_PHONE_EXSITER_ERROR = new CodeMsg(-90002, "手机号已经存在了");
	public static CodeMsg COMMON_PAY_PASSWORD_ERROR = new CodeMsg(-90003, "支付密码错误");


	//前台用户登录错误码
	public static CodeMsg EMAIL_NULL_ERROR = new CodeMsg(-11000,"请输入邮箱!");
	public static CodeMsg PASSWORD_NULL_ERROR = new CodeMsg(-11001,"请输入登录密码!");
	public static CodeMsg EMAIL_NOT_REGISTER = new CodeMsg(-11002,"邮箱错误!");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(-11003,"登录密码错误!");
	public static CodeMsg HOME_USER_UNABLE = new CodeMsg(-11004, "该用户状态不可用，请联系管理员！");


	//前台用户编辑错误码
	public static CodeMsg USER_NAME_NULL_ERROR = new CodeMsg(-12000,"用户名不能为空!");
	public static CodeMsg USER_NAME_LENGTH_ERROR = new CodeMsg(-12001,"用户名长度需在2-18位!");
	public static CodeMsg NAME_LENGTH_ERROR = new CodeMsg(-12002,"姓名最多18位!");
	public static CodeMsg HOME_USER_EDIT_ERROR = new CodeMsg(-12003,"编辑失败!");
	public static CodeMsg HOME_USER_EDIT_HEAD_PIC_ERROR = new CodeMsg(-12004,"头像更新失败!");
	public static CodeMsg PASSWORD_LENGTH_ERROR = new CodeMsg(-12005,"密码长度需在4-32位!");
	public static CodeMsg PAY_PASSWORD_LENGTH_ERROR = new CodeMsg(-12006,"支付密码长度需为6位!");
	public static CodeMsg UPDATE_STATUS_ERROR = new CodeMsg(-12007,"状态修改失败!");


	//银行卡管理错误码
	public static CodeMsg BANK_CARD_ERROR = new CodeMsg(-13000,"请输入正确的银行卡号！");
	public static CodeMsg CARD_BANK_ERROR = new CodeMsg(-13001,"银行卡号和银行不匹配！");
	public static CodeMsg CARD_HAS_ERROR = new CodeMsg(-13002,"该银行卡已被绑定！");
	public static CodeMsg CARD_ADD_ERROR = new CodeMsg(-13003,"银行卡添加失败，请联系管理员！");
	public static CodeMsg CARD_DELETE_ERROR = new CodeMsg(-13004,"银行卡删除失败！");

	//前台用户提现管理错误码
	public static CodeMsg MONEY_NULL_ERROR = new CodeMsg(-14000,"请输入提现金额！");
	public static CodeMsg BANK_CARD_NULL_ERROR = new CodeMsg(-14001,"请选择银行卡！");
	public static CodeMsg MONET_MIN_ERROR = new CodeMsg(-14002,"提现金额需大于0！");
	public static CodeMsg MONET_MAX_ERROR = new CodeMsg(-14003,"提现金额不能大于100000！");
	public static CodeMsg BANK_CARD_ID_NULL_ERROR = new CodeMsg(-14004,"该银行卡信息不存在！");
	public static CodeMsg BANK_CARD_HOME_USER_ERROR = new CodeMsg(-14005,"银行卡信息与登录人信息不匹配！");
	public static CodeMsg WITHDRAWAL_RECORD_SAVE_ERROR = new CodeMsg(-14006,"提现申请发送失败！");

	//后台用户提现管理错误码
	public static CodeMsg WITHDRAWAL_NO_EXIST = new CodeMsg(-15000,"申请不存在！");
	public static CodeMsg WITHDRAWAL_HAS_APPROVAL = new CodeMsg(-15001,"该申请已审批！");
	public static CodeMsg WITHDRAWAL_APPROVAL_ERROR = new CodeMsg(-15002,"审批失败！");
	public static CodeMsg WITHDRAWAL_MONEY_MORE_BALANCE = new CodeMsg(-15003,"本次申请的金额已超过该用户当前余额！");
	public static CodeMsg WITHDRAWAL_NOT_PASS_REASON_LONG_ERROR = new CodeMsg(-15004,"理由最长128位！");

	//支付中标项目错误码
	public static CodeMsg RECORD_HOME_USER_ERROR = new CodeMsg(-16000,"数据错误！");
	public static CodeMsg BIDDING_STATUS_ERROR = new CodeMsg(-16001,"你不是中标人！");
	public static CodeMsg BIDDING_PROJECT_STATUS_ERROR = new CodeMsg(-16002,"项目该状态无法支付！");
	public static CodeMsg BIDDING_PAY_STATUS_ERROR = new CodeMsg(-16003,"已支付，无法再支付！");
	public static CodeMsg BIDDING_OVERDUE_STATUS_ERROR = new CodeMsg(-16004,"已逾期，无法再支付！");
	public static CodeMsg PAY_MONEY_ERROR = new CodeMsg(-16005,"支付失败！");
	public static CodeMsg BALANCE_LESS_ERROR = new CodeMsg(-16006,"余额不足，请先充值！");
	public static CodeMsg BALANCE_UPDATE_ERROR=new CodeMsg(-16007,"余额更新失败,请联系管理员");

	//提醒相关错误码
	public static CodeMsg HOME_REMIND_ADD_ERROR = new CodeMsg(-18000,"添加提醒失败！");
	public static CodeMsg HOME_REMIND_EXIST_ADD_ERROR = new CodeMsg(-18001,"你已经设置了提醒！");
	public static CodeMsg HOME_REMIND_DELETE_ERROR = new CodeMsg(-18002,"取消提醒失败！");
	public static CodeMsg HOME_REMIND_NOT_EXIST_DELETE_ERROR = new CodeMsg(-18003,"这不是你的提醒！");
	public static CodeMsg NOT_APPLY_ERROR = new CodeMsg(-18004,"还未报名，不能添加提醒！");
	public static CodeMsg PROJECT_STATUS_ERROR = new CodeMsg(-18005,"竞拍已开始或者已结束，不能设置提醒！");

	//报名错误码
	public static CodeMsg HOME_SIGN_UP_ADD_ERROR=new CodeMsg(-17000,"报名添加失败,请联系管理员!");
	public static CodeMsg HOME_SIGN_UP_EXIST_ERROR=new CodeMsg(-17001,"该项目你已经报过名，不可重复报名!");
	public static CodeMsg HOME_SIGN_UP_NOT_EXIST_ERROR=new CodeMsg(-17002,"你未报名该项目,不能报价,很抱歉");

	//留言回复错误码
	public static CodeMsg HOME_MESSAGE_ADD_ERROR=new CodeMsg(-19000,"留言添加失败!");

	//竞价错误码
	public static CodeMsg HOME_BIDDING_PROJECT_PERSON_MANY=new CodeMsg(-20000,"哎呦喂，人太多了！");
	public static CodeMsg HOME_BIDDING_RECORD_ADD=new CodeMsg(-20001,"竞价记录添加失败,请联系管理员");
	public static CodeMsg HOME_BIDDING_RECORD_BID_MIN_ERROR=new CodeMsg(-20002,"已经有人报过该价格了,请重新竞价");
	public static CodeMsg HOME_BIDDING_PROJECT_STATUS_ERROR=new CodeMsg(-20003,"当前项目状态不可竞价");
	public static CodeMsg HOME_BIDDING_PROJECT_PERSON_MANY_ERROR=new CodeMsg(-20004,"竞价人数过多,请刷新页面重新竞价");
	public static CodeMsg HOME_BIDDING_PROJECT_START_PRICE_ERROR=new CodeMsg(-20005,"竞拍价格不能小于起拍价");
	public static CodeMsg HOME_BIDDING_PROJECT_AUCTIONTIMES_ADD_ERROR=new CodeMsg(-20006,"竞拍次数更新失败");
	public static CodeMsg HOME_BIDDING_PROJECT_APPLICANTSNUMBER_UPDATE_ERROR=new CodeMsg(-20007,"竞拍报名人数更新失败,请联系管理员");
	public static CodeMsg HOME_BIDDING_PROJECT_ENDTIME_UPDATE_ERROR=new CodeMsg(-20008,"项目结束延长错误,请联系管理员");
	public static CodeMsg HOME_BIDDING_PROJECT_MONEY_ERROR=new CodeMsg(-20009,"竞拍人数过多,请刷新页面重新竞拍");
	public static CodeMsg HOME_BIDDING_PROJECT_CURRENT_PRICE_ERROR=new CodeMsg(-20010,"当前价更新失败,请联系管理员");
}
