package hexlet.code.schema;

import static hexlet.code.schema.PredicateUtils.notRequiredPredicate;
import static hexlet.code.schema.PredicateUtils.requiredPredicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addCheck(CheckType.REQUIRED, requiredPredicate(number -> true));
        return this;
    }

    public NumberSchema positive() {
        addCheck(CheckType.POSITIVE, notRequiredPredicate(number -> number > 0));
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck(CheckType.RANGE, notRequiredPredicate(number -> number >= min && number <= max));
        return this;
    }
}
