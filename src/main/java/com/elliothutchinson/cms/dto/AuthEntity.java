package com.elliothutchinson.cms.dto;

public class AuthEntity<T> {

    private String auth;
    private T entity;

    protected AuthEntity() {
    }

    public AuthEntity(String auth, T entity) {
        this.auth = auth;
        this.entity = entity;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
