package dev.sandrocaseiro.template.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.util.Streamable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Data
public class DPage<T> implements Streamable<T> {
    private List<T> data;

    private boolean lastPage;

    private int totalPages;

    private long totalItems;

    private int currentPage;

    @JsonIgnore
    @Override
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return data != null ? data.iterator() : Collections.emptyIterator();
    }

    public void add(T item) {
        if (data == null)
            data = new ArrayList<>();

        data.add(item);
    }
}
