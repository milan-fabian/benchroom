package sk.mimac.benchroom.web;

/**
 *
 * @author Milan Fabian
 */
public class ValueLabelWrapper {

    private Object value;
    private Object label;

    public ValueLabelWrapper() {
    }

    public ValueLabelWrapper(Object value, Object label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

}
