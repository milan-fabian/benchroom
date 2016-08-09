package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public abstract class AbstractFilter<T extends Dto> {

    private int pageSize;

    private int pageNumber;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
