package com.iisigroup.cap.base.dao;

import java.util.Map;

import com.iisigroup.cap.base.model.Sequence;
import com.iisigroup.cap.component.Request;
import com.iisigroup.cap.db.dao.GenericDao;
import com.iisigroup.cap.db.dao.SearchSetting;
import com.iisigroup.cap.db.model.Page;

public interface SequenceDao extends GenericDao<Sequence> {
    Page<Map<String, Object>> findForSequencePage(SearchSetting search, Request params);

    void createFromMap(Map<String, Object> map);

    int updateByNodeAndNextSeqFromMap(Map<String, Object> map);

    Sequence findBySeqNode(String seqNode);

}
