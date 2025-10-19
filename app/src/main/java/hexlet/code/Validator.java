package hexlet.code;

import hexlet.code.schema.MapSchema;
import hexlet.code.schema.NumberSchema;
import hexlet.code.schema.StringSchema;

public final class Validator {

    public StringSchema string() {
        return new StringSchema();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }

    public MapSchema map() {
        return new MapSchema();
    }
}
