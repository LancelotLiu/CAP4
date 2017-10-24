/* 
 * CapUserDetail.java
 * 
 * Copyright (c) 2009-2012 International Integrated System, Inc. 
 * All Rights Reserved.
 * 
 * Licensed Materials - Property of International Integrated System, Inc.
 * 
 * This software is confidential and proprietary information of 
 * International Integrated System, Inc. (&quot;Confidential Information&quot;).
 */
package com.iisigroup.cap.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.JsonArray;

/**
 * <pre>
 * CapUserDetails
 * </pre>
 * 
 * @since 2012/5/15
 * @author rodeschen
 * @version
 *          <ul>
 *          <li>2012/5/15,rodeschen,new
 *          <li>2013/3/6,rodeschen,add set method
 *          </ul>
 */
@SuppressWarnings("serial")
public class CapUserDetails implements UserDetails {

    private String password;
    private String userId;
    private String username;
    private String unitNo;
    private Map<String, String> roles;
    private JsonArray menu;
    private Locale locale;
    private String status;
    private Map<String, Object> extraAttrib;

    private Collection<GrantedAuthority> authorities;

    public CapUserDetails() {
    }

    public CapUserDetails(User user, String password, Map<String, String> roles) {
        this.password = password;
        this.userId = user.getCode();
        this.username = user.getName();
        this.unitNo = user.getDepCode();
        this.roles = new LinkedHashMap<String, String>();
        this.roles.putAll(roles);
        this.locale = user.getLocale();
        this.status = user.getStatus();
        this.extraAttrib = new HashMap<String, Object>();
        setAuthorities(roles);
    }

    protected void setAuthorities(Map<String, String> roles) {
        authorities = new ArrayList<GrantedAuthority>();
        for (String roleOid : roles.keySet()) {
            authorities.add(new SimpleGrantedAuthority(roleOid));
        }
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public JsonArray getMenu() {
        return menu;
    }

    public void setMenu(JsonArray menu) {
        this.menu = menu;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, String> roles) {
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;

    }

    public void setUserName(String userName) {
        this.username = userName;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getExtraAttrib() {
        return extraAttrib == null ? new HashMap<String, Object>() : extraAttrib;
    }

    public void setExtraAttrib(Map<String, Object> extraAttrib) {
        this.extraAttrib = extraAttrib;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CapUserDetails other = (CapUserDetails) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    // TODO Mark by sk
    // public Map<String, Object> put(String k, Object v) {
    // this.extraAttrib.put(k, v);
    // return extraAttrib;
    // }
    // @SuppressWarnings("unchecked")
    // public <T> T get(String k) {
    // return (T) this.extraAttrib.get(k);
    // }

}
