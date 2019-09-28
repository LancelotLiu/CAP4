/* 
 * I18nDaoImpl.java
 * 
 * Copyright (c) 2019 International Integrated System, Inc. 
 * All Rights Reserved.
 * 
 * Licensed Materials - Property of International Integrated System, Inc.
 * 
 * This software is confidential and proprietary information of 
 * International Integrated System, Inc. (&quot;Confidential Information&quot;).
 */
package com.iisigroup.cap.base.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.iisigroup.cap.base.dao.I18nDao;
import com.iisigroup.cap.base.model.I18n;
import com.iisigroup.cap.db.constants.SearchMode;
import com.iisigroup.cap.db.dao.SearchSetting;
import com.iisigroup.cap.db.dao.impl.GenericDaoImpl;

/**
 * <pre>
 * I18n DAO
 * </pre>
 * 
 * @since 2010/12/9
 * @author iristu
 * @version
 *          <ul>
 *          <li>2010/12/9,iristu,new
 *          <li>2011/11/20,RodesChen,from cap
 *          </ul>
 */
@Repository
public class I18nDaoImpl extends GenericDaoImpl<I18n> implements I18nDao {

    @Override
    public List<I18n> findByCodeType(String codetype, String locale) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "locale", locale);
        search.addSearchModeParameters(SearchMode.EQUALS, "codeType", codetype);
        search.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
        search.addOrderBy("codeOrder");
        List<I18n> list = find(search);
        return list;
    }

    @Override
    public I18n findByCodeTypeAndCodeValue(String cType, String cValue, String locale) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "locale", locale);
        search.addSearchModeParameters(SearchMode.EQUALS, "codeType", cType);
        search.addSearchModeParameters(SearchMode.EQUALS, "codeValue", cValue);
        search.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
        return findUniqueOrNone(search);
    }

    @Override
    public List<I18n> findByCodeType(String[] codetypes, String locale) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "locale", locale);
        search.addSearchModeParameters(SearchMode.IN, "codeType", codetypes);
        search.setFirstResult(0).setMaxResults(Integer.MAX_VALUE);
        search.addOrderBy("codeOrder");
        return find(search);
    }

    @Override
    public List<I18n> findByCodeTypeAndCodeDesc(String cType, String codeDesc, String locale) {
        SearchSetting search = createSearchTemplete();
        search.addSearchModeParameters(SearchMode.EQUALS, "locale", locale);
        search.addSearchModeParameters(SearchMode.EQUALS, "codeType", cType);
        search.addSearchModeParameters(SearchMode.EQUALS, "codeDesc", codeDesc);
        return find(search);
    }

    @Override
    public I18n findByOid(String oid) {
        return find(oid);
    }

    @Override
    public Map<String, I18n> findAsMapByCodeType(String codetype, String locale) {
        List<I18n> list = findByCodeType(codetype, locale);
        Map<String, I18n> map = new HashMap<String, I18n>();
        for (I18n i18n : list) {
            map.put(i18n.getCodeValue(), i18n);
        }
        return map;
    }

}
