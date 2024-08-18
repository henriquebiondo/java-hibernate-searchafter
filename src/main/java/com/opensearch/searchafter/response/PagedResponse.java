package com.opensearch.searchafter.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse<T> {

    private List<T> content;
    private Integer page;
    private Integer size;
    private long totalElements;
    private Integer lastId;
    private int totalPages;

    public PagedResponse(List<T> content, int page, int size, long totalElements, Integer lastId) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.lastId = lastId;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

}
