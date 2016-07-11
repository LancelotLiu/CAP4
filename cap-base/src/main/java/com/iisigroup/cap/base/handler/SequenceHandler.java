/* 
 * SequenceHandler.java
 * 
 * Copyright (c) 2009-2012 International Integrated System, Inc. 
 * All Rights Reserved.
 * 
 * Licensed Materials - Property of International Integrated System, Inc.
 * 
 * This software is confidential and proprietary information of 
 * International Integrated System, Inc. (&quot;Confidential Information&quot;).
 */
package com.iisigroup.cap.base.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.iisigroup.cap.annotation.HandlerType;
import com.iisigroup.cap.annotation.HandlerType.HandlerTypeEnum;
import com.iisigroup.cap.base.service.SequenceService;
import com.iisigroup.cap.component.Request;
import com.iisigroup.cap.component.Result;
import com.iisigroup.cap.component.impl.AjaxFormResult;
import com.iisigroup.cap.component.impl.MapGridResult;
import com.iisigroup.cap.db.dao.SearchSetting;
import com.iisigroup.cap.db.model.Page;
import com.iisigroup.cap.mvc.handler.MFormHandler;

/**
 * <pre>
 * 流水號
 * </pre>
 * 
 * @since 2012/10/25
 * @author iristu
 * @version
 *          <ul>
 *          <li>2012/10/25,iristu,new
 *          </ul>
 */
@Controller("sequencehandler")
public class SequenceHandler extends MFormHandler {

    @Resource
    private SequenceService seqSrv;

    @HandlerType(HandlerTypeEnum.GRID)
    public MapGridResult query(SearchSetting search, Request params) {

        Page<Map<String, Object>> page = seqSrv.findPage(search.getFirstResult(), search.getMaxResults());
        return new MapGridResult(page.getContent(), page.getTotalRow());
    }

    public Result getNewSeq(Request params) {
        AjaxFormResult result = new AjaxFormResult();
        String seqNode = params.get("seqNode");
        int theSeq = seqSrv.getNextSeqNo(seqNode, 1, 1, Integer.MAX_VALUE);
        result.set("theSeq", theSeq);
        return result;
    }

}
