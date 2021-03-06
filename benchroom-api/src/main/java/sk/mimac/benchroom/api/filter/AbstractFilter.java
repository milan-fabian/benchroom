package sk.mimac.benchroom.api.filter;

/**
 *
 * @author Milan Fabian
 */
public abstract class AbstractFilter {

    private int pageSize = 50;

    private int pageNumber = 1;

    private String fulltext;

    private String orderBy;

    private OrderDirection orderDir;

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

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public OrderDirection getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(OrderDirection orderDir) {
        this.orderDir = orderDir;
    }

    public enum OrderDirection {

        ASC, DESC;
    }
}
