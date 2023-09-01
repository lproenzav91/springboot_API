package com.testandres.repaso.controller.model;

import java.util.List;

public class ResultResponse {

    private int page;
    private List<Result> results;

    public ResultResponse() {
    }

    public ResultResponse(int page, List<Result> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
