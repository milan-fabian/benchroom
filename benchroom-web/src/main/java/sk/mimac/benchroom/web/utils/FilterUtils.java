package sk.mimac.benchroom.web.utils;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import sk.mimac.benchroom.api.filter.AbstractFilter;

/**
 *
 * @author Milan Fabian
 */
public class FilterUtils {

    public static void setDataTableToFilter(DataTablesInput dataTable, AbstractFilter filter) {
        filter.setPageNumber((dataTable.getStart() / dataTable.getLength()) + 1);
        filter.setPageSize(dataTable.getLength());
        filter.setFulltext(dataTable.getSearch().getValue());
        filter.setOrderBy(dataTable.getColumns().get(dataTable.getOrder().get(0).getColumn()).getData());
        filter.setOrderDir(AbstractFilter.OrderDirection.valueOf(dataTable.getOrder().get(0).getDir().toUpperCase()));
    }

}
