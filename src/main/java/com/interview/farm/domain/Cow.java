package com.interview.farm.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cow {
    private String id;
    private List<String> children;
    private String nickname;

    public String getId() {
        return id;
    }

    public Cow setId(String id) {
        this.id = id;
        return this;
    }

    public Cow addChild(String cowId) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(cowId);
        return this;
    }

    public List<String> getChildren() {
        if (children == null) return Collections.emptyList();
        return new ArrayList<>(children);
    }

    public String getNickname() {
        return nickname;
    }

    public Cow setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
