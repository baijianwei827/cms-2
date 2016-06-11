package com.elliothutchinson.cms.dto;

public class ForeignEntity {

    private Long id;
    private String name;
    private boolean current;

    protected ForeignEntity() {
    }

    public ForeignEntity(Long id, String name, boolean current) {
        this.id = id;
        this.name = name;
        this.current = current;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
