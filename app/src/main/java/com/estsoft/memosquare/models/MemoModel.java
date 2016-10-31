package com.estsoft.memosquare.models;

/**
 * Created by sun on 2016-10-25.
 */

public class MemoModel {
    private int pk;
    private String title;
    private String content;
    private String owner;
    private String page;
    private int[] clipper;
    private boolean is_private;
    private String timestamp;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean is_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    @Override
    public String toString() {
        return "MemoModel{" +
                "pk=" + pk +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", owner='" + owner + '\'' +
                ", page='" + page + '\'' +
                ", is_private=" + is_private +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
