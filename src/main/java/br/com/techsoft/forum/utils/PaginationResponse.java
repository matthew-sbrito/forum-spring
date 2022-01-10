package br.com.techsoft.forum.utils;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationResponse<T> {

    private List<T> list;
    private Integer currentPage;
    private Long    totalItems;
    private Integer totalPages;

    public PaginationResponse(
            List<T> list,
            Page page
    ) {
        this.list        = list;
        this.currentPage = page.getNumber() + 1;
        this.totalItems  = page.getTotalElements();
        this.totalPages  = page.getTotalPages();
    }

    public List<T> getList() {
        return list;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
