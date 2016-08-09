package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.SoftwareDto;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareFilter extends AbstractFilter<SoftwareDto> {

    private String fulltext;

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

}
