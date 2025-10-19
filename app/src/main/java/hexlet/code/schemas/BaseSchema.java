package hexlet.code.schemas;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final Map<CheckType, Predicate<T>> checks = new EnumMap<>(CheckType.class);

    protected final void addCheck(CheckType type, Predicate<T> check) {
        checks.put(type, check);
    }

    public final boolean isValid(T value) {
        return checks.values().stream().allMatch(check -> check.test(value));
    }
}
