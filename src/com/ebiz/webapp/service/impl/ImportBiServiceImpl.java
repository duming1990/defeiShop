package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.ImportBiDao;
import com.ebiz.webapp.dao.ImportBiSonDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.ImportBi;
import com.ebiz.webapp.domain.ImportBiSon;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.ImportBiService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
@Service
public class ImportBiServiceImpl extends BaseImpl implements ImportBiService {

    @Resource
    private ImportBiDao importBiDao;

    @Resource
    private ImportBiSonDao importBiSonDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private UserBiRecordDao userBiRecordDao;

    @Resource
    private BaseDataDao baseDataDao;

    public Integer createImportBi(ImportBi t) {
        int id = this.importBiDao.insertEntity(t);
        if (null != t.getImportBiSonList()) {
            List<ImportBiSon> list = t.getImportBiSonList();
            for (ImportBiSon importBiSon : list) {
                importBiSon.setLink_id(id);
                importBiSonDao.insertEntity(importBiSon);
            }
        }
        return id;
    }

    public ImportBi getImportBi(ImportBi t) {
        return this.importBiDao.selectEntity(t);
    }

    public Integer getImportBiCount(ImportBi t) {
        return this.importBiDao.selectEntityCount(t);
    }

    public List<ImportBi> getImportBiList(ImportBi t) {
        return this.importBiDao.selectEntityList(t);
    }

    public int modifyImportBi(ImportBi t) {
        if (null != t.getMap().get("insert_bi") && null != t.getId()) {
            ImportBiSon importBiSon = new ImportBiSon();
            importBiSon.setLink_id(t.getId());
            List<ImportBiSon> list = importBiSonDao.selectEntityList(importBiSon);
            for (ImportBiSon cur : list) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(cur.getUser_id());
                userInfo.setIs_del(0);
                userInfo = userInfoDao.selectEntity(userInfo);
                if (null == userInfo) {
                    continue;
                }
                BigDecimal rmb_to_dianzibi = super.BiToBi2(cur.getBi_no(),
                        Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);
                super.insertUserWelfareBuyBiRecord(cur.getUser_id(), null, Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex(),
                        null, cur.getId(), rmb_to_dianzibi, Keys.BiType.BI_TYPE_100.getIndex(),
                        Keys.BiGetType.BI_GET_TYPE_4000.getIndex(), null, userInfoDao, userBiRecordDao, rmb_to_dianzibi);
                UserInfo updateUser = new UserInfo();
                updateUser.setId(userInfo.getId());
                updateUser.getMap().put("add_bi_dianzi", rmb_to_dianzibi);
                updateUser.getMap().put("add_bi_welfare", rmb_to_dianzibi);
                userInfoDao.updateEntity(updateUser);
                StringBuffer message = new StringBuffer("{\"money\":\""
                        + rmb_to_dianzibi.setScale(2, BigDecimal.ROUND_DOWN) + "\",");
                message.append("\"mobilePhone\":\"" + userInfo.getMobile() + "\",");
                message.append("}");
                DySmsUtils.sendMessage(message.toString(), userInfo.getMobile(), SmsTemplate.sms_25);
            }
        }
        if (null != t.getMap().get("update_audit")) {
            ImportBiSon update = new ImportBiSon();
            update.getMap().put("link_id", t.getId());
            update.setAudit_state(t.getAudit_state());
            update.setAudit_date(t.getAudit_date());
            importBiSonDao.updateEntity(update);
        }
        return this.importBiDao.updateEntity(t);
    }

    public int removeImportBi(ImportBi t) {
        if (null != t.getMap().get("del_son")) {
            ImportBi importBi = new ImportBi();
            importBi.setId(t.getId());
            importBi = importBiDao.selectEntity(importBi);
            if (null != importBi && importBi.getAudit_state().intValue() == 1) {
                return -1;
            }
            ImportBi entity = new ImportBi();
            entity.setId(t.getId());
            entity.setIs_del(0);
            entity = importBiDao.selectEntity(entity);
            if (null != entity) {
                ImportBiSon del = new ImportBiSon();
                del.setIs_del(0);
                del.getMap().put("link_id", entity.getId());
                importBiSonDao.deleteEntity(del);
            }
        }
        if (null != t.getMap().get("pks")) {
            String[] pks = (String[]) t.getMap().get("pks");
            if (null != pks && pks.length > 0) {
                for (String string : pks) {
                    ImportBi importBi = new ImportBi();
                    importBi.setId(Integer.valueOf(string));
                    importBi = importBiDao.selectEntity(importBi);
                    if (null != importBi && importBi.getAudit_state().intValue() == 1) {
                        return -1;
                    }
                }
                for (String string : pks) {
                    logger.info("string:" + string);
                    ImportBi entity = new ImportBi();
                    entity.setId(Integer.valueOf(string));
                    entity.setIs_del(0);
                    entity = importBiDao.selectEntity(entity);
                    if (null != entity) {
                        ImportBiSon del = new ImportBiSon();
                        del.getMap().put("link_id", entity.getId());
                        importBiSonDao.deleteEntity(del);
                    }
                }
            }
        }
        return this.importBiDao.deleteEntity(t);
    }

    public List<ImportBi> getImportBiPaginatedList(ImportBi t) {
        return this.importBiDao.selectEntityPaginatedList(t);
    }
}
