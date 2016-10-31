package com.estsoft.memosquare.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sun on 2016-10-27.
 * API response: count, next, previous, results로 이루어짐.
 * 그 중 results가 우리가 원하는 model list.
 * results만 꺼내기 위한 class
 */

public class ModelList<model> {

    @SerializedName("results")
    private List<model> modellist;

    public List<model> getModellist() {
        return modellist;
    }

    public void setModellist(List<model> modellist) {
        this.modellist = modellist;
    }

    @Override
    public String toString() {
        return "ModelList{" +
                "modellist=" + modellist +
                '}';
    }
}
