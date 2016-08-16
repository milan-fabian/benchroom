package sk.mimac.benchroom.web;

import java.util.List;
import sk.mimac.benchroom.api.dto.Page;

/**
 *
 * @author Milan Fabian
 */
public class PageWrapper {

    private final List data;

    private final long recordsTotal;
    private final long recordsFiltered;

    public PageWrapper(Page page) {
        this.data = page.getElements();
        this.recordsTotal = page.getTotalElements();
        this.recordsFiltered = page.getTotalElements();
    }

    public List getData() {
        return data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

}
