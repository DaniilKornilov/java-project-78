package hexlet.code.schema;

public class StringSchema {
    private boolean required = false;
    private Integer minLength = null;
    private String contains = null;

    public StringSchema required() {
        removeValidators();
        this.required = true;
        return this;
    }

    public StringSchema minLength(int length) {
        removeValidators();
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substr) {
        removeValidators();
        this.contains = substr;
        return this;
    }

    public boolean isValid(Object value) {
        if (value == null) {
            return !required;
        }
        if (!(value instanceof String s)) {
            return false;
        }
        if (s.isEmpty()) {
            return !required;
        }
        if (minLength != null && s.length() < minLength) {
            return false;
        }
        return contains == null || s.contains(contains);
    }

    private void removeValidators() {
        this.required = false;
        this.minLength = null;
        this.contains = null;
    }
}
