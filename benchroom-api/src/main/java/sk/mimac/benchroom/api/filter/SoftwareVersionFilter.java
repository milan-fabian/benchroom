package sk.mimac.benchroom.api.filter;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareVersionFilter extends AbstractFilter {

    private Long softwareId;

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

}
