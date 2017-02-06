package com.aweiz.dzmvc.request;

/**
 * Created by daweizhuang on 2/2/17.
 */
public class WebRequest {
    private String url;
    private Boolean isRest;
    private String httpMethod;
    private String produce;

    public WebRequest(String url) {
        this.url = url;
    }

    public WebRequest(String url, Boolean isRest, String httpMethod, String produce) {
        this.url = url;
        this.isRest = isRest;
        this.httpMethod = httpMethod;
        this.produce = produce;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getRest() {
        return isRest;
    }

    public void setRest(Boolean rest) {
        isRest = rest;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WebRequest that = (WebRequest) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(url, that.url)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(url)
                .toHashCode();
    }
}
