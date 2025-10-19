package hexlet.code.schema;

import java.util.Map;

import static hexlet.code.schema.PredicateUtils.notRequiredPredicate;
import static hexlet.code.schema.PredicateUtils.requiredPredicate;

public class MapSchema extends BaseSchema<Map<Object, Object>> {

    public MapSchema required() {
        addCheck(CheckType.REQUIRED, requiredPredicate(Map.class::isInstance));
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck(CheckType.SIZE_OF, notRequiredPredicate(map -> map.size() == size));
        return this;
    }
}
