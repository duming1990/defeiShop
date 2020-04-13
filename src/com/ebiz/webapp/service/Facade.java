package com.ebiz.webapp.service;

import com.ebiz.ssi.service.BaseFacade;
import com.ebiz.webapp.domain.CartInfo;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface Facade extends BaseFacade {

	ActivityService getActivityService();

	ActivityApplyService getActivityApplyService();

	ActivityApplyCommService getActivityApplyCommService();

	CardInfoService getCardInfoService();

	CardPIndexService getCardPIndexService();

	CardApplyService getCardApplyService();

	CardGenHisService getCardGenHisService();

	CommWelfareApplyService getCommWelfareApplyService();

	CommWelfareDetailService getCommWelfareDetailService();

	ImportBiService getImportBiService();

	ImportBiSonService getImportBiSonService();

	YhqLinkService getYhqLinkService();

	PoorCuoShiService getPoorCuoShiService();

	PoorZeRenService getPoorZeRenService();

	PoorFamilyService getPoorFamilyService();

	VillageDynamicReportService getVillageDynamicReportService();

	VillageContactGroupService getVillageContactGroupService();

	VillageContactListService getVillageContactListService();

	VillageDynamicCommentService getVillageDynamicCommentService();

	VillageDynamicRecordService getVillageDynamicRecordService();

	VillageDynamicService getVillageDynamicService();

	VillageMemberService getVillageMemberService();

	CommentInfoSonService getCommentInfoSonService();

	OrderReturnInfoService getOrderReturnInfoService();

	YhqInfoService getYhqInfoService();

	PoorInfoService getPoorInfoService();

	YhqInfoSonService getYhqInfoSonService();

	VillageInfoService getVillageInfoService();

	OrderReturnAuditService getOrderReturnAuditService();

	OrderReturnMoneyService getOrderReturnMoneyService();

	OrderReturnMsgService getOrderReturnMsgService();

	CommInfoPromotionService getCommInfoPromotionService();

	BaseComminfoTagsService getBaseComminfoTagsService();

	CommInfoTagsService getCommInfoTagsService();

	AutoRunService getAutoRunService();

	BaseAttributeService getBaseAttributeService();

	BaseAttributeSonService getBaseAttributeSonService();

	BaseAuditRecordService getBaseAuditRecordService();

	BaseBrandInfoService getBaseBrandInfoService();

	BaseClassLinkAttributeService getBaseClassLinkAttributeService();

	BaseClassService getBaseClassService();

	BaseContentService getBaseContentService();

	BaseDataService getBaseDataService();

	BaseFilesService getBaseFilesService();

	BaseImgsService getBaseImgsService();

	BaseLinkService getBaseLinkService();

	BasePopedomService getBasePopedomService();

	BaseProvinceService getBaseProvinceService();

	CartInfoService getCartInfoService();

	CommentInfoService getCommentInfoService();

	CommInfoService getCommInfoService();

	CommTczhAttributeService getCommTczhAttributeService();

	CommTczhPriceService getCommTczhPriceService();

	DiscountStoresService getDiscountStoresService();

	EntpContentService getEntpContentService();

	EntpInfoService getEntpInfoService();

	FreightDetailService getFreightDetailService();

	FreightService getFreightService();

	HelpContentService getHelpContentService();

	HelpInfoService getHelpInfoService();

	HelpModuleService getHelpModuleService();

	InvoiceInfoService getInvoiceInfoService();

	MailServerService getMailServerService();

	MBaseLinkService getMBaseLinkService();

	ModPopedomService getModPopedomService();

	MsgReceiverService getMsgReceiverService();

	MsgService getMsgService();

	NewsAttachmentService getNewsAttachmentService();

	NewsContentService getNewsContentService();

	NewsInfoService getNewsInfoService();

	OrderInfoDetailsService getOrderInfoDetailsService();

	OrderInfoService getOrderInfoService();

	OrderLinkInfoService getOrderLinkInfoService();

	OrderMergerInfoService getOrderMergerInfoService();

	PdContentService getPdContentService();

	PdImgsService getPdImgsService();

	QaInfoService getQaInfoService();

	RankTopService getRankTopService();

	ResetPasswordRecordService getResetPasswordRecordService();

	ReturnsInfoFjService getReturnsInfoFjService();

	ReturnsInfoOrderRelationService getReturnsInfoOrderRelationService();

	ReturnsInfoService getReturnsInfoService();

	ReturnsSwapDetailService getReturnsSwapDetailService();

	RoleService getRoleService();

	RoleUserService getRoleUserService();

	RwHbInfoService getRwHbInfoService();

	RwHbRuleService getRwHbRuleService();

	RwHbYhqService getRwHbYhqService();

	RwYhqInfoService getRwYhqInfoService();

	RwYhqRuleService getRwYhqRuleService();

	ScEntpCommService getScEntpCommService();

	ServiceCenterInfoService getServiceCenterInfoService();

	ShippingAddressService getShippingAddressService();

	SwapsInfoService getSwapsInfoService();

	SysModuleService getSysModuleService();

	SysOperLogService getSysOperLogService();

	SysSettingService getSysSettingService();

	TongjiService getTongjiService();

	UserBiRecordService getUserBiRecordService();

	UserInfoService getUserInfoService();

	UserJifenRecordService getUserJifenRecordService();

	UserMoneyApplyService getUserMoneyApplyService();

	UserRelationParService getUserRelationParService();

	UserRelationService getUserRelationService();

	UserScoreRecordService getUserScoreRecordService();

	UserSecurityService getUserSecurityService();

	WlCompInfoService getWlCompInfoService();

	WlCompMatflowService getWlCompMatflowService();

	WlOrderInfoService getWlOrderInfoService();

	WlWhInfoService getWlWhInfoService();

	CartInfo getCartInfoCountForSumCount(CartInfo t);

	OrderAuditService getOrderAuditService();

	CommInfoPoorsService getCommInfoPoorsService();

	SensitiveWordService getSensitiveWordService();

	JdAreasService getJdAreasService();

	AppInfoService getAppInfoService();

	AppTokensService getAppTokensService();

	ServiceBaseLinkService getServiceBaseLinkService();

	EntpBaseLinkService getEntpBaseLinkService();

	EntpDuiZhangService getEntpDuiZhangService();

	MServiceBaseLinkService getMServiceBaseLinkService();

	ShortMessageService getShortMessageService();

	ShortMessageReceiverService getShortMessageReceiverService();

	EntpCommClassService getEntpCommClassService();

	VillageTourService getVillageTourService();

}
