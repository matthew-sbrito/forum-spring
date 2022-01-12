package br.com.techsoft.forum.utils;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationResponse<T> {

    private List<T> content;
    private Integer currentPage;
    private Long    totalItems;
    private Integer totalPages;

    public PaginationResponse(
            List<T> content,
            Page page
    ) {
        this.content     = content;
        this.currentPage = page.getNumber() + 1;
        this.totalItems  = page.getTotalElements();
        this.totalPages  = page.getTotalPages();
    }

    public List<T> getList() {
        return content;
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
