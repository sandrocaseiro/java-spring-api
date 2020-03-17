package com.sandrocaseiro.apitemplate.mappers;

import com.sandrocaseiro.apitemplate.models.DPage;
import org.springframework.data.domain.Page;

public final class PageMapper {
    private PageMapper() {

    }

    public static <T> DPage<T> toDPage(Page<T> model) {
        DPage<T> page = new DPage<>();
        page.setData(model.getContent());
        page.setLastPage(model.isLast());
        page.setTotalPages(model.getTotalPages());
        page.setTotalItems(model.getTotalElements());
        page.setCurrentPage(model.getPageable().getPageNumber() + 1);

        return page;
    }
}
