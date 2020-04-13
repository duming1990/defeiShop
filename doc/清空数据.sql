delete FROM user_info where id <> 1;
ALTER TABLE `user_info` AUTO_INCREMENT=10;
delete FROM entp_info where id <> 1;
ALTER TABLE `entp_info` AUTO_INCREMENT=10;
delete FROM user_relation where id <> 1;
ALTER TABLE `user_relation` AUTO_INCREMENT=10;
TRUNCATE table entp_content;


delete FROM role_user where role_id <> 1;

#####################################################
TRUNCATE table user_bi_record;
TRUNCATE table user_jifen_record;
TRUNCATE table user_score_record;
TRUNCATE table user_relation_par;
TRUNCATE table user_money_apply;
TRUNCATE table service_center_info;

truncate table base_files;
truncate table base_imgs;
truncate table base_link;

##############商品和进货对应的表###########################
truncate table comm_info;
truncate table comm_stock_info;
truncate table comm_tczh_price;
truncate table pd_content;
truncate table pd_imgs;
truncate table comm_info_promotion;
truncate table comm_info_tags;
truncate table comm_tczh_attribute;

-- 新闻表帮助中心表
truncate table news_attachment;
truncate table help_content;
truncate table help_info;
delete FROM help_module where id <> 1;
truncate table cart_info;
-- 订单表
truncate table order_info;
truncate table order_info_details;
truncate table order_merger_info;
truncate table wl_order_info;
truncate table comment_info;
truncate table comment_info_son;

-- 站内信表
truncate table  msg;
truncate table  msg_receiver;

-- truncate table tongji;
delete from tongji where tongji_type not in(10,40);
update tongji t set t.TONGJI_NUM1 =0, t.TONGJI_NUM2=0, t.TONGJI_NUM3=0;
update tongji t set t.TONGJI_NUM1 = 1 where t.tongji_type = 10;

truncate table base_audit_record;
truncate table base_brand_info;
truncate table base_files;

truncate table freight;
truncate table freight_detail;
truncate table invoice_info;
truncate table qa_info;
truncate table sys_oper_log;


-- 只更新卡数据，订单数据和用户币记录数据
TRUNCATE table user_bi_record;
TRUNCATE table user_score_record;
truncate table user_jifen_record;


UPDATE user_info t
SET t.BI_DIANZI_LOCK_TUIGUAN = 0,
 t.BI_DIANZI_LOCK = 0,
 t.BI_DIANZI_LOST = 0,
 t.LEIJI_MONEY_TUIGUANG = 0,
 t.bi_xiaofei = 0,
 t.CUR_SCORE = 0,
 t.USER_SCORE_UNION = 0;


TRUNCATE table sc_entp_comm;
TRUNCATE table shipping_address;
TRUNCATE table user_security;

-- 清空村站表
delete FROM village_contact_group where id <> 0;
TRUNCATE table village_contact_list;
TRUNCATE table village_dynamic;
TRUNCATE table village_dynamic_comment;
TRUNCATE table village_dynamic_record;
TRUNCATE table village_dynamic_report;
TRUNCATE table village_info;
TRUNCATE table village_member;
TRUNCATE table poor_info;
TRUNCATE table comm_info_poors;
-- 清空评价
TRUNCATE table order_return_info;
TRUNCATE table order_return_audit;
TRUNCATE table order_return_money;
TRUNCATE table order_return_msg;
