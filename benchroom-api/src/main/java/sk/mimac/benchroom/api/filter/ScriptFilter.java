package sk.mimac.benchroom.api.filter;

/**
 *
 * @author Milan Fabian
 */
public class ScriptFilter extends AbstractFilter {

    private Long softwareVersionId;

    public Long getSoftwareVersionId() {
        return softwareVersionId;
    }

    public void setSoftwareVersionId(Long softwareVersionId) {
        this.softwareVersionId = softwareVersionId;
    }

}
