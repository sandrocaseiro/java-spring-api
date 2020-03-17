package com.sandrocaseiro.apitemplate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

@AllArgsConstructor
@Data
public class DPageable {
    public static final int PAGE_SIZE = 10;
    public static final int CURRENT_PAGE = 1;

    private int currentPage = CURRENT_PAGE;
    private int pageSize = PAGE_SIZE;
    private List<Sort> sort;

    public Pageable asPageable() {
        org.springframework.data.domain.Sort pageableSort = null;
        for (Sort fieldSort : sort) {
            if (pageableSort == null)
                pageableSort = org.springframework.data.domain.Sort.by(fieldSort.isDescending() ? Direction.DESC : Direction.ASC, fieldSort.getField());
            else
                pageableSort.and(org.springframework.data.domain.Sort.by(fieldSort.isDescending() ? Direction.DESC : Direction.ASC, fieldSort.getField()));
        }

        if (pageableSort == null)
            return PageRequest.of(currentPage - 1, pageSize);
        else
            return PageRequest.of(currentPage - 1, pageSize, pageableSort);
    }

    @AllArgsConstructor
    @Data
    public static class Sort {
        private String field;
        private boolean isDescending;
    }
}
