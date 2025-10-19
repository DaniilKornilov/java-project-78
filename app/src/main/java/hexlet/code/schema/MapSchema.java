package hexlet.code.schema;

import java.util.Map;

import static hexlet.code.schema.PredicateUtils.notRequiredPredicate;
import static hexlet.code.schema.PredicateUtils.requiredPredicate;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema required() {
        addCheck(CheckType.REQUIRED, requiredPredicate(Map.class::isInstance));
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck(CheckType.SIZE_OF, notRequiredPredicate(map -> map.size() == size));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> MapSchema shape(Map<String, BaseSchema<T>> schemas) {
        addCheck(CheckType.SHAPE, notRequiredPredicate(map ->
                schemas.entrySet()
                        .stream()
                        .allMatch(entry -> {
                            String key = entry.getKey();
                            BaseSchema<T> schema = entry.getValue();
                            T value = (T) map.get(key);
                            return schema.isValid(value);
                        })));
        return this;
    }
}
