package hexlet.code.schema;

import java.util.function.Predicate;

final class PredicateUtils {

    static <T> Predicate<T> notRequiredPredicate(Predicate<T> predicate) {
        return value -> value == null || predicate.test(value);
    }

    static <T> Predicate<T> requiredPredicate(Predicate<T> predicate) {
        return value -> value != null && predicate.test(value);
    }
}
