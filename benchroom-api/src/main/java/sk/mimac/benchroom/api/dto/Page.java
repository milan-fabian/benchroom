package sk.mimac.benchroom.api.dto;

import java.util.List;

/**
 *
 * @author Milan Fabian
 */
public class Page<T extends Dto> {

    private final List<T> elements;

    private final int pageNumber;

    private final int pageSize;

    private final long totalElements;

    public Page(List<T> elements, int pageNumber, int pageSize, long totalElements) {
        this.elements = elements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

}
