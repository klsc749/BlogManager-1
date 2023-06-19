package com.example.blogmanager.util;


import lombok.Data;

@Data
public class PageQueryUtil {

    // The current page number
    private int page;

    // The maximum number of items per page
    private int limit;

    // The starting index for the current page
    private int start;

    /**
     * Constructor for PageQueryUtil.
     *
     * @param page  the current page number
     * @param limit the maximum number of items per page
     */
    public PageQueryUtil(int page, int limit) {
        this.page = page;
        this.limit = limit;
        this.start = (page - 1) * limit;
    }
}
