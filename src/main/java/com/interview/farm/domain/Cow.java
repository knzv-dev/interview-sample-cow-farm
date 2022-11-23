package com.interview.farm.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public void removeChild(String cowId) {
        if (children != null) {
            children.remove(cowId);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cow cow = (Cow) o;
        return Objects.equals(id, cow.id) && Objects.equals(children, cow.children) && Objects.equals(nickname, cow.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, children, nickname);
    }
}
