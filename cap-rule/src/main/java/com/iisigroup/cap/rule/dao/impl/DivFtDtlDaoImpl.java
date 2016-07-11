/*
 * Copyright (c) 2009-2012 International Integrated System, Inc. 
 * 11F, No.133, Sec.4, Minsheng E. Rd., Taipei, 10574, Taiwan, R.O.C.
 * All Rights Reserved.
 * 
 * Licensed Materials - Property of International Integrated System, Inc.
 * 
 * This software is confidential and proprietary information of 
 * International Integrated System, Inc. (&quot;Confidential Information&quot;).
 */

package com.iisigroup.cap.rule.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iisigroup.cap.db.constants.SearchMode;
import com.iisigroup.cap.db.dao.SearchSetting;
import com.iisigroup.cap.db.dao.impl.GenericDaoImpl;
import com.iisigroup.cap.rule.dao.DivFtDtlDao;
import com.iisigroup.cap.rule.model.DivFtDtl;
import com.iisigroup.cap.utils.CapString;

/**
 * <pre>
 * Division Fator Details DAO Impl
 * </pre>
 * 
 * @since 2013/12/19
 * @author TimChiang
 * @version
 *          <ul>
 *          <li>2013/12/19,TimChiang,new
 *          </ul>
 */
@Repository
public class DivFtDtlDaoImpl extends GenericDaoImpl<DivFtDtl> implements DivFtDtlDao {

    @Override
    public DivFtDtl findByFactorNo(String factorNo) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "factorNo", factorNo);
        return findUniqueOrNone(search);
    }

    @Override
    public List<DivFtDtl> findByFactorNos(String[] factorNos) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "factorNo", factorNos);
        search.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
        search.addOrderBy("codeOrder");
        return find(search);
    }

    @Override
    public DivFtDtl findByOid(String oid) {
        return find(oid);
    }

    @Override
    public DivFtDtl findByFactorNoAndRangeNo(String factorNo, String rangeNos) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "factorNo", factorNo);
        search.addSearchModeParameters(SearchMode.EQUALS, "rangeNo", rangeNos);
        search.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
        search.addOrderBy("rangeNo");
        return findUniqueOrNone(search);
    }

    @Override
    public List<DivFtDtl> findByFactorNoAndRangeNos(String factorNo, String[] rangeNos) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "factorNo", factorNo);
        search.addSearchModeParameters(SearchMode.EQUALS, "rangeNo", rangeNos);
        search.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
        search.addOrderBy("rangeNo");
        return find(search);
    }

    @Override
    public void merge(List<DivFtDtl> divFtDtls) {
        for (DivFtDtl ftDtl : divFtDtls) {
            if (!CapString.isEmpty(ftDtl.getOid()))
                merge(ftDtl);
        }
    }

}
