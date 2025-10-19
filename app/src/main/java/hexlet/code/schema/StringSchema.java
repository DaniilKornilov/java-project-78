package hexlet.code.schema;

import static hexlet.code.schema.PredicateUtils.notRequiredPredicate;
import static hexlet.code.schema.PredicateUtils.requiredPredicate;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addCheck(CheckType.REQUIRED, requiredPredicate(string -> !string.isEmpty()));
        return this;
    }

    public StringSchema minLength(int minLength) {
        addCheck(CheckType.MIN_LENGTH, notRequiredPredicate(string -> string.length() >= minLength));
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck(CheckType.CONTAINS, notRequiredPredicate(string -> string.contains(substring)));
        return this;
    }
}
