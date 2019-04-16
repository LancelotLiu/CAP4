package com.iisigroup.colabase.service;

import com.iisigroup.colabase.model.RequestContent;
import com.iisigroup.colabase.model.ResponseContent;

public interface SslClient<T extends ResponseContent> {

    /**
     * 送出API request
     * @param requestContent request model
     * @return 回傳繼承ResponseContent的
     */
    T sendRequest(RequestContent requestContent);

    /**
     * 送出API request，並在Header中附加citi專屬的值
     * @param requestContent request model
     * @return 回傳繼承ResponseContent的
     */
    T sendRequestWithDefaultHeader(RequestContent requestContent);

}
