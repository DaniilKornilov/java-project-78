package hexlet.code.schema;

import java.util.Objects;

import static hexlet.code.schema.PredicateUtils.notRequiredPredicate;

public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addCheck(CheckType.REQUIRED, Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addCheck(CheckType.POSITIVE, notRequiredPredicate(value -> value > 0));
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck(CheckType.RANGE, notRequiredPredicate(value -> value >= min && value <= max));
        return this;
    }
}
