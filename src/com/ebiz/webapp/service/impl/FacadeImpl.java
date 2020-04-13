package com.ebiz.webapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ebiz.ssi.service.impl.BaseFacadeImpl;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.service.*;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Component("facade")
public class FacadeImpl extends BaseFacadeImpl implements Facade {

	@Resource
	ActivityService activityService;

	@Resource
	ActivityApplyService activityApplyService;

	@Resource
	ActivityApplyCommService activityApplyCommService;

	public ActivityService getActivityService() {
		return activityService;
	}

	public ActivityApplyService getActivityApplyService() {
		return activityApplyService;
	}

	public ActivityApplyCommService getActivityApplyCommService() {
		return activityApplyCommService;
	}

	@Resource
	CardInfoService cardInfoService;

	@Resource
	CardPIndexService cardPIndexService;

	@Resource
	CardApplyService cardApplyService;

	@Resource
	CardGenHisService cardGenHisService;

	public CardInfoService getCardInfoService() {
		return cardInfoService;
	}

	public CardPIndexService getCardPIndexService() {
		return cardPIndexService;
	}

	public CardApplyService getCardApplyService() {
		return cardApplyService;
	}

	public CardGenHisService getCardGenHisService() {
		return cardGenHisService;
	}

	@Resource
	CommWelfareApplyService commWelfareApplyService;

	@Resource
	CommWelfareDetailService commWelfareDetailService;

	public CommWelfareApplyService getCommWelfareApplyService() {
		return commWelfareApplyService;
	}

	public CommWelfareDetailService getCommWelfareDetailService() {
		return commWelfareDetailService;
	}

	@Resource
	ImportBiService importBiService;

	@Resource
	ImportBiSonService importBiSonService;

	public ImportBiService getImportBiService() {
		return importBiService;
	}

	public ImportBiSonService getImportBiSonService() {
		return importBiSonService;
	}

	@Resource
	YhqLinkService yhqLinkService;

	public YhqLinkService getYhqLinkService() {
		return yhqLinkService;
	}

	@Resource
	VillageDynamicReportService villageDynamicReportService;

	public VillageDynamicReportService getVillageDynamicReportService() {
		return villageDynamicReportService;
	}

	@Resource
	VillageContactGroupService villageContactGroupService;

	@Resource
	VillageContactListService villageContactListService;

	@Resource
	VillageDynamicCommentService villageDynamicCommentService;

	@Resource
	VillageDynamicRecordService villageDynamicRecordService;

	@Resource
	VillageDynamicService villageDynamicService;

	@Resource
	VillageMemberService villageMemberService;

	@Resource
	PoorCuoShiService poorCuoShiService;

	@Resource
	PoorZeRenService poorZeRenService;

	@Resource
	PoorFamilyService poorFamilyService;

	public PoorCuoShiService getPoorCuoShiService() {
		return poorCuoShiService;
	}

	public PoorZeRenService getPoorZeRenService() {
		return poorZeRenService;
	}

	public PoorFamilyService getPoorFamilyService() {
		return poorFamilyService;
	}

	public VillageContactGroupService getVillageContactGroupService() {
		return villageContactGroupService;
	}

	public VillageContactListService getVillageContactListService() {
		return villageContactListService;
	}

	public VillageDynamicCommentService getVillageDynamicCommentService() {
		return villageDynamicCommentService;
	}

	public VillageDynamicRecordService getVillageDynamicRecordService() {
		return villageDynamicRecordService;
	}

	public VillageDynamicService getVillageDynamicService() {
		return villageDynamicService;
	}

	public VillageMemberService getVillageMemberService() {
		return villageMemberService;
	}

	@Resource
	PoorInfoService poorInfoService;

	public PoorInfoService getPoorInfoService() {
		return poorInfoService;
	}

	@Resource
	CommentInfoSonService commentInfoSonService;

	public CommentInfoSonService getCommentInfoSonService() {
		return commentInfoSonService;
	}

	@Resource
	VillageInfoService villageInfoService;

	public VillageInfoService getVillageInfoService() {
		return villageInfoService;
	}

	@Resource
	CommInfoPromotionService commInfoPromotionService;

	public CommInfoPromotionService getCommInfoPromotionService() {
		return commInfoPromotionService;
	}

	@Resource
	RwHbInfoService rwHbInfoService;

	@Resource
	YhqInfoService yhqInfoService;

	@Resource
	YhqInfoSonService yhqInfoSonService;

	public YhqInfoService getYhqInfoService() {
		return yhqInfoService;
	}

	public YhqInfoSonService getYhqInfoSonService() {
		return yhqInfoSonService;
	}

	@Resource
	RwHbRuleService rwHbRuleService;

	@Resource
	BaseComminfoTagsService baseComminfoTagsService;

	@Resource
	CommInfoTagsService commInfoTagsService;

	public BaseComminfoTagsService getBaseComminfoTagsService() {
		return baseComminfoTagsService;
	}

	public CommInfoTagsService getCommInfoTagsService() {
		return commInfoTagsService;
	}

	@Resource
	RwYhqInfoService rwYhqInfoService;

	@Resource
	RwHbYhqService rwHbYhqService;

	@Resource
	RwYhqRuleService rwYhqRuleService;

	@Resource
	AutoRunService autoRunService;

	@Resource
	BaseAttributeService baseAttributeService;

	@Resource
	BaseAttributeSonService baseAttributeSonService;

	@Resource
	BaseBrandInfoService baseBrandInfoService;

	@Resource
	BaseClassLinkAttributeService baseClassLinkAttributeService;

	@Resource
	BaseClassService baseClassService;

	@Resource
	BaseContentService baseContentService;

	@Resource
	BaseDataService baseDataService;

	@Resource
	BaseFilesService baseFilesService;

	@Resource
	BaseImgsService baseImgsService;

	@Resource
	BaseLinkService baseLinkService;

	@Resource
	BasePopedomService basePopedomService;

	@Resource
	BaseProvinceService baseProvinceService;

	@Resource
	CartInfoService cartInfoService;

	@Resource
	CommentInfoService commentInfoService;

	@Resource
	CommInfoService commInfoService;

	@Resource
	CommTczhAttributeService commTczhAttributeService;

	@Resource
	CommTczhPriceService commTczhPriceService;

	@Resource
	DiscountStoresService discountStoresService;

	@Resource
	EntpInfoService entpInfoService;

	@Resource
	FreightDetailService freightDetailService;

	@Resource
	FreightService freightService;

	@Resource
	HelpContentService helpContentService;

	@Resource
	HelpInfoService helpInfoService;

	@Resource
	HelpModuleService helpModuleService;

	@Resource
	InvoiceInfoService invoiceInfoService;

	@Resource
	MailServerService mailServerService;

	@Resource
	MBaseLinkService mBaseLinkService;

	@Resource
	ModPopedomService modPopedomService;

	@Resource
	MsgReceiverService msgReceiverService;

	@Resource
	MsgService msgService;

	@Resource
	NewsAttachmentService newsAttachmentService;

	@Resource
	NewsContentService newsContentService;

	@Resource
	NewsInfoService newsInfoService;

	@Resource
	OrderInfoDetailsService orderInfoDetailsService;

	@Resource
	OrderInfoService orderInfoService;

	@Resource
	OrderLinkInfoService orderLinkInfoService;

	@Resource
	OrderMergerInfoService orderMergerInfoService;

	@Resource
	PdContentService pdContentService;

	@Resource
	PdImgsService pdImgsService;

	@Resource
	QaInfoService qaInfoService;

	@Resource
	RankTopService rankTopService;

	@Resource
	ResetPasswordRecordService resetPasswordRecordService;

	@Resource
	ReturnsInfoFjService returnsInfoFjService;

	@Resource
	ReturnsInfoOrderRelationService returnsInfoOrderRelationService;

	@Resource
	ReturnsInfoService returnsInfoService;

	@Resource
	ReturnsSwapDetailService returnsSwapDetailService;

	@Resource
	RoleService roleService;

	@Resource
	RoleUserService roleUserService;

	@Resource
	ScEntpCommService scEntpCommService;

	@Resource
	ServiceCenterInfoService serviceCenterInfoService;

	@Resource
	ShippingAddressService shippingAddressService;

	@Resource
	SwapsInfoService swapsInfoService;

	@Resource
	SysModuleService sysModuleService;

	@Resource
	SysOperLogService sysOperLogService;

	@Resource
	SysSettingService sysSettingService;

	@Resource
	TongjiService tongjiService;

	@Resource
	UserBiRecordService userBiRecordService;

	@Resource
	UserInfoService userInfoService;

	@Resource
	UserJifenRecordService userJifenRecordService;

	@Resource
	UserMoneyApplyService userMoneyApplyService;

	@Resource
	UserRelationParService userRelationParService;

	@Resource
	UserRelationService userRelationService;

	@Resource
	UserScoreRecordService userScoreRecordService;

	@Resource
	UserSecurityService userSecurityService;

	@Resource
	WlCompInfoService wlCompInfoService;

	@Resource
	WlCompMatflowService wlCompMatflowService;

	@Resource
	WlOrderInfoService wlOrderInfoService;

	@Resource
	WlWhInfoService wlWhInfoService;

	@Resource
	BaseAuditRecordService baseAuditRecordService;

	@Resource
	EntpContentService entpContentService;

	@Resource
	OrderReturnInfoService orderReturnInfoService;

	@Resource
	OrderReturnAuditService orderReturnAuditService;

	@Resource
	OrderReturnMoneyService orderReturnMoneyService;

	@Resource
	OrderReturnMsgService orderReturnMsgService;

	@Resource
	OrderAuditService orderAuditService;

	public OrderReturnInfoService getOrderReturnInfoService() {
		return orderReturnInfoService;
	}

	public OrderReturnAuditService getOrderReturnAuditService() {
		return orderReturnAuditService;
	}

	public OrderReturnMoneyService getOrderReturnMoneyService() {
		return orderReturnMoneyService;
	}

	public OrderReturnMsgService getOrderReturnMsgService() {
		return orderReturnMsgService;
	}

	@Override
	public AutoRunService getAutoRunService() {
		return autoRunService;
	}

	@Override
	public BaseAttributeService getBaseAttributeService() {
		return baseAttributeService;
	}

	@Override
	public BaseAttributeSonService getBaseAttributeSonService() {
		return baseAttributeSonService;
	}

	public BaseAuditRecordService getBaseAuditRecordService() {
		return baseAuditRecordService;
	}

	@Override
	public BaseBrandInfoService getBaseBrandInfoService() {
		return baseBrandInfoService;
	}

	@Override
	public BaseClassLinkAttributeService getBaseClassLinkAttributeService() {
		return baseClassLinkAttributeService;
	}

	@Override
	public BaseClassService getBaseClassService() {
		return baseClassService;
	}

	@Override
	public BaseContentService getBaseContentService() {
		return baseContentService;
	}

	@Override
	public BaseDataService getBaseDataService() {
		return baseDataService;
	}

	@Override
	public BaseFilesService getBaseFilesService() {
		return baseFilesService;
	}

	@Override
	public BaseImgsService getBaseImgsService() {
		return baseImgsService;
	}

	@Override
	public BaseLinkService getBaseLinkService() {
		return baseLinkService;
	}

	@Override
	public BasePopedomService getBasePopedomService() {
		return basePopedomService;
	}

	@Override
	public BaseProvinceService getBaseProvinceService() {
		return baseProvinceService;
	}

	@Override
	public CartInfoService getCartInfoService() {
		return cartInfoService;
	}

	@Override
	public CommentInfoService getCommentInfoService() {
		return commentInfoService;
	}

	@Override
	public CommInfoService getCommInfoService() {
		return commInfoService;
	}

	@Override
	public CommTczhAttributeService getCommTczhAttributeService() {
		return commTczhAttributeService;
	}

	@Override
	public CommTczhPriceService getCommTczhPriceService() {
		return commTczhPriceService;
	}

	@Override
	public DiscountStoresService getDiscountStoresService() {
		return discountStoresService;
	}

	public EntpContentService getEntpContentService() {
		return entpContentService;
	}

	@Override
	public EntpInfoService getEntpInfoService() {
		return entpInfoService;
	}

	@Override
	public FreightDetailService getFreightDetailService() {
		return freightDetailService;
	}

	@Override
	public FreightService getFreightService() {
		return freightService;
	}

	@Override
	public HelpContentService getHelpContentService() {
		return helpContentService;
	}

	@Override
	public HelpInfoService getHelpInfoService() {
		return helpInfoService;
	}

	@Override
	public HelpModuleService getHelpModuleService() {
		return helpModuleService;
	}

	@Override
	public InvoiceInfoService getInvoiceInfoService() {
		return invoiceInfoService;
	}

	@Override
	public MailServerService getMailServerService() {
		return mailServerService;
	}

	@Override
	public MBaseLinkService getMBaseLinkService() {
		return mBaseLinkService;
	}

	@Override
	public ModPopedomService getModPopedomService() {
		return modPopedomService;
	}

	@Override
	public MsgReceiverService getMsgReceiverService() {
		return msgReceiverService;
	}

	@Override
	public MsgService getMsgService() {
		return msgService;
	}

	@Override
	public NewsAttachmentService getNewsAttachmentService() {
		return newsAttachmentService;
	}

	@Override
	public NewsContentService getNewsContentService() {
		return newsContentService;
	}

	@Override
	public NewsInfoService getNewsInfoService() {
		return newsInfoService;
	}

	@Override
	public OrderInfoDetailsService getOrderInfoDetailsService() {
		return orderInfoDetailsService;
	}

	@Override
	public OrderInfoService getOrderInfoService() {
		return orderInfoService;
	}

	@Override
	public OrderLinkInfoService getOrderLinkInfoService() {
		return orderLinkInfoService;
	}

	@Override
	public OrderMergerInfoService getOrderMergerInfoService() {
		return orderMergerInfoService;
	}

	@Override
	public PdContentService getPdContentService() {
		return pdContentService;
	}

	@Override
	public PdImgsService getPdImgsService() {
		return pdImgsService;
	}

	@Override
	public QaInfoService getQaInfoService() {
		return qaInfoService;
	}

	@Override
	public RankTopService getRankTopService() {
		return rankTopService;
	}

	@Override
	public ResetPasswordRecordService getResetPasswordRecordService() {
		return resetPasswordRecordService;
	}

	@Override
	public ReturnsInfoFjService getReturnsInfoFjService() {
		return returnsInfoFjService;
	}

	@Override
	public ReturnsInfoOrderRelationService getReturnsInfoOrderRelationService() {
		return returnsInfoOrderRelationService;
	}

	@Override
	public ReturnsInfoService getReturnsInfoService() {
		return returnsInfoService;
	}

	@Override
	public ReturnsSwapDetailService getReturnsSwapDetailService() {
		return returnsSwapDetailService;
	}

	@Override
	public RoleService getRoleService() {
		return roleService;
	}

	@Override
	public RoleUserService getRoleUserService() {
		return roleUserService;
	}

	@Override
	public RwHbInfoService getRwHbInfoService() {
		return rwHbInfoService;
	}

	@Override
	public RwHbRuleService getRwHbRuleService() {
		return rwHbRuleService;
	}

	@Override
	public RwHbYhqService getRwHbYhqService() {
		return rwHbYhqService;
	}

	@Override
	public RwYhqInfoService getRwYhqInfoService() {
		return rwYhqInfoService;
	}

	@Override
	public RwYhqRuleService getRwYhqRuleService() {
		return rwYhqRuleService;
	}

	@Override
	public ScEntpCommService getScEntpCommService() {
		return scEntpCommService;
	}

	@Override
	public ServiceCenterInfoService getServiceCenterInfoService() {
		return serviceCenterInfoService;
	}

	@Override
	public ShippingAddressService getShippingAddressService() {
		return shippingAddressService;
	}

	@Override
	public SwapsInfoService getSwapsInfoService() {
		return swapsInfoService;
	}

	@Override
	public SysModuleService getSysModuleService() {
		return sysModuleService;
	}

	@Override
	public SysOperLogService getSysOperLogService() {
		return sysOperLogService;
	}

	@Override
	public SysSettingService getSysSettingService() {
		return sysSettingService;
	}

	@Override
	public TongjiService getTongjiService() {
		return tongjiService;
	}

	@Override
	public UserBiRecordService getUserBiRecordService() {
		return userBiRecordService;
	}

	@Override
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public UserJifenRecordService getUserJifenRecordService() {
		return userJifenRecordService;
	}

	@Override
	public UserMoneyApplyService getUserMoneyApplyService() {
		return userMoneyApplyService;
	}

	@Override
	public UserRelationParService getUserRelationParService() {
		return userRelationParService;
	}

	@Override
	public UserRelationService getUserRelationService() {
		return userRelationService;
	}

	@Override
	public UserScoreRecordService getUserScoreRecordService() {
		return userScoreRecordService;
	}

	@Override
	public UserSecurityService getUserSecurityService() {
		return userSecurityService;
	}

	@Override
	public WlCompInfoService getWlCompInfoService() {
		return wlCompInfoService;
	}

	@Override
	public WlCompMatflowService getWlCompMatflowService() {
		return wlCompMatflowService;
	}

	@Override
	public WlOrderInfoService getWlOrderInfoService() {
		return wlOrderInfoService;
	}

	@Override
	public WlWhInfoService getWlWhInfoService() {
		return wlWhInfoService;
	}

	public void setAutoRunService(AutoRunService autoRunService) {
		this.autoRunService = autoRunService;
	}

	@Override
	public CartInfo getCartInfoCountForSumCount(CartInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	public OrderAuditService getOrderAuditService() {
		return orderAuditService;
	}

	@Resource
	CommInfoPoorsService commInfoPoorsService;

	public CommInfoPoorsService getCommInfoPoorsService() {
		return commInfoPoorsService;
	}

	@Resource
	SensitiveWordService sensitiveWordService;

	public SensitiveWordService getSensitiveWordService() {
		return sensitiveWordService;
	}

	@Resource
	JdAreasService jdAreasService;

	public JdAreasService getJdAreasService() {
		return jdAreasService;
	}

	@Resource
	AppInfoService appInfoService;

	@Resource
	AppTokensService appTokensService;

	public AppInfoService getAppInfoService() {
		return appInfoService;
	}

	public AppTokensService getAppTokensService() {
		return appTokensService;
	}

	@Resource
	ServiceBaseLinkService serviceBaseLinkService;

	public ServiceBaseLinkService getServiceBaseLinkService() {
		return serviceBaseLinkService;
	}

	@Resource
	EntpBaseLinkService entpBaseLinkService;

	public EntpBaseLinkService getEntpBaseLinkService() {
		return entpBaseLinkService;
	}

	@Resource
	MServiceBaseLinkService mServiceBaseLinkService;

	public MServiceBaseLinkService getMServiceBaseLinkService() {
		return mServiceBaseLinkService;
	}

	@Resource
	EntpDuiZhangService entpDuiZhangService;

	public EntpDuiZhangService getEntpDuiZhangService() {
		return entpDuiZhangService;
	}

	@Resource
	ShortMessageService shortMessageService;

	public ShortMessageService getShortMessageService() {
		return shortMessageService;
	}

	@Resource
	ShortMessageReceiverService shortMessageReceiverService;

	public ShortMessageReceiverService getShortMessageReceiverService() {
		return shortMessageReceiverService;
	}

	@Resource
	EntpCommClassService entpCommClassService;

	public EntpCommClassService getEntpCommClassService() {
		return entpCommClassService;
	}

	@Resource
	VillageTourService villageTourService;

	public VillageTourService getVillageTourService() {
		return villageTourService;
	}
}
