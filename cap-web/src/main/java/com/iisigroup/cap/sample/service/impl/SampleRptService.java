/* 
 * SampleRptService.java
 * 
 * Copyright (c) 2011 International Integrated System, Inc. 
 * All Rights Reserved.
 * 
 * Licensed Materials - Property of International Integrated System, Inc.
 * 
 * This software is confidential and proprietary information of 
 * International Integrated System, Inc. (&quot;Confidential Information&quot;).
 */
package com.iisigroup.cap.sample.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iisigroup.cap.component.Request;
import com.iisigroup.cap.report.AbstractReportPdfService;
import com.iisigroup.cap.report.constants.ReportParamEnum;
import com.iisigroup.cap.utils.CapDate;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class SampleRptService extends AbstractReportPdfService {

    @Override
    public Map<String, Object> execute(Request request) {
        Map<String, Object> reportData = new HashMap<String, Object>();
        /* 建立資料模型 */
        reportData.put("userId", "userId");
        reportData.put("dataRange", "2014/01/01~2014/02/02");
        reportData.put("printDate", CapDate.getCurrentDate("yyyy-MM-dd"));
        reportData.put("printer", "printer");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        int count = 1;
        while (count <= 10) {
            count++;
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", "userId" + count);
            map.put("ipAddress", "ipAddress" + count);
            map.put("funcName", "funcName" + count);
            map.put("actionName", "actionName" + count);
            map.put("execDate", CapDate.convertTimestampToString(CapDate.getCurrentTimestamp(), "yyyy-MM-dd HH:mm:ss"));
            list.add(map);
        }
        int totalPageNo = 1;
        if (!CollectionUtils.isEmpty(list) && list.size() > 30) {
            int totalCount = list.size();
            totalCount -= 30;
            totalPageNo = totalCount / 35 + 2;
        }
        reportData.put("totalPageNo", String.valueOf(totalPageNo));
        reportData.put("mapList", list);

        // 加密密碼
        reportData.put(ReportParamEnum.ENCRYPT.toString(), request.get("password"));

        return reportData;
    }

    @Override
    public String getReportDefinition() {
        return "report/samplePDF";
    }

    @Override
    protected int getAllowedPrivileges() {
        return PdfWriter.ALLOW_SCREENREADERS;// 允許螢幕閱讀(複製內容用於協助工具)
    }
}
