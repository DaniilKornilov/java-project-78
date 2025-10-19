package hexlet.code.schema;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final Map<CheckType, Predicate<T>> checks = new HashMap<>();

    protected void addCheck(CheckType type, Predicate<T> check) {
        checks.put(type, check);
    }

    public boolean isValid(T value) {
        return checks.values().stream().allMatch(check -> check.test(value));
    };
}
