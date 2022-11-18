package com.interview.farm.domain;

public class Cow {
    private String id;
    private String parentId;
    private String nickname;
    private boolean isAlive = true;

    public String getId() {
        return id;
    }

    public Cow setId(String id) {
        this.id = id;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Cow setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Cow setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Cow setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
        return this;
    }
}
