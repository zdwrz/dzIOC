package com.aweiz.dzmvc.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by daweizhuang on 2/2/17.
 */
public class WebRequest {
    private String url;
    private String httpMethod = "ALL";

    public WebRequest() {}

    public WebRequest(String url) {
        this.url = url;
    }

    public WebRequest(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WebRequest that = (WebRequest) o;

        return new EqualsBuilder()
                .append(url, that.url)
                .append(httpMethod, that.httpMethod)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(url)
                .append(httpMethod)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "WebRequest{" +
                "url='" + url + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
