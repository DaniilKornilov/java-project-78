package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public final class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void prepareValidator() {
        validator = new Validator();
    }

    @Nested
    class StringSchemaTest {
        private static final int MIN_LENGTH = 10;

        private StringSchema schema;

        @BeforeEach
        void prepareSchema() {
            schema = validator.string();
        }

        @Test
        void testWithoutSchema() {
            assertThat(schema.isValid(null)).isTrue();
            assertThat(schema.isValid("")).isTrue();
        }

        @Test
        void testRequiredSchema() {
            assertThat(schema.required().isValid(null)).isFalse();
            assertThat(schema.required().isValid("")).isFalse();
            assertThat(schema.required().isValid("what does the fox say")).isTrue();
            assertThat(schema.required().isValid("hexlet")).isTrue();
        }

        @Test
        void testContainsSchema() {
            assertThat(schema.contains("wh").isValid(null)).isTrue();
            assertThat(schema.contains("wh").isValid("what does the fox say")).isTrue();
            assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();
            assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();
        }

        @Test
        void testMinLengthSchema() {
            assertThat(schema.minLength(MIN_LENGTH).isValid(null)).isTrue();
            assertThat(schema.minLength(MIN_LENGTH).isValid("Hexlet")).isFalse();
            assertThat(schema.minLength(MIN_LENGTH).isValid("what does the fox say")).isTrue();
        }

        @Test
        void testMultipleSchemas() {
            assertThat(schema.minLength(MIN_LENGTH).minLength(1).isValid("Hexlet")).isTrue();
            assertThat(schema.required().contains("what").minLength(MIN_LENGTH).isValid("what does the fox")).isTrue();
        }
    }

    @Nested
    class NumberSchemaTest {
        private static final int LOWER_BOUND = 5;
        private static final int UPPER_BOUND = 10;

        private NumberSchema schema;

        @BeforeEach
        void prepareSchema() {
            schema = validator.number();
        }

        @Test
        void testWithoutSchema() {
            assertThat(schema.isValid(null)).isTrue();
            assertThat(schema.isValid(1)).isTrue();
        }

        @Test
        void testRequiredSchema() {
            assertThat(schema.required().isValid(null)).isFalse();
            assertThat(schema.required().isValid(1)).isTrue();
        }

        @Test
        void testPositiveSchema() {
            assertThat(schema.positive().isValid(null)).isTrue();
            assertThat(schema.positive().isValid(-1)).isFalse();
            assertThat(schema.positive().isValid(0)).isFalse();
            assertThat(schema.positive().isValid(1)).isTrue();
        }

        @Test
        void testRangeSchema() {
            assertThat(schema.range(LOWER_BOUND, UPPER_BOUND).isValid(null)).isTrue();
            assertThat(schema.range(LOWER_BOUND, UPPER_BOUND).isValid(LOWER_BOUND)).isTrue();
            assertThat(schema.range(LOWER_BOUND, UPPER_BOUND).isValid(UPPER_BOUND)).isTrue();
            assertThat(schema.range(LOWER_BOUND, UPPER_BOUND).isValid(LOWER_BOUND - 1)).isFalse();
            assertThat(schema.range(LOWER_BOUND, UPPER_BOUND).isValid(UPPER_BOUND + 1)).isFalse();
        }

        @Test
        void testMultipleSchemas() {
            assertThat(schema.range(LOWER_BOUND, UPPER_BOUND).range(0, 1).isValid(1)).isTrue();
            assertThat(schema.required().positive().range(LOWER_BOUND, UPPER_BOUND).isValid(1)).isFalse();
        }
    }

    @Nested
    class MapSchemaTest {
        private static final int KEY1 = 1;
        private static final int VALUE1 = 2;
        private static final int KEY2 = 3;
        private static final int VALUE2 = 4;

        private MapSchema schema;

        @BeforeEach
        void prepareSchema() {
            schema = validator.map();
        }

        @Test
        void testWithoutSchema() {
            assertThat(schema.isValid(null)).isTrue();
            assertThat(schema.isValid(Map.of())).isTrue();
        }

        @Test
        void testRequiredSchema() {
            assertThat(schema.required().isValid(null)).isFalse();
            assertThat(schema.required().isValid(Map.of())).isTrue();
        }

        @Test
        void testSizeSchema() {
            assertThat(schema.sizeof(2).isValid(null)).isTrue();
            assertThat(schema.sizeof(2).isValid(Map.of(KEY1, VALUE1))).isFalse();
            assertThat(schema.sizeof(2).isValid(Map.of(KEY1, VALUE1, KEY2, VALUE2))).isTrue();
        }

        @Test
        void testShapeSchema() {
            Map<String, BaseSchema<String>> schemas = new HashMap<>();
            schemas.put("firstName", validator.string().required());
            schemas.put("lastName", validator.string().required().minLength(2));

            Map<String, String> human1 = new HashMap<>();
            human1.put("firstName", "John");
            human1.put("lastName", "Smith");
            assertThat(schema.shape(schemas).isValid(human1)).isTrue();

            Map<String, String> human2 = new HashMap<>();
            human2.put("firstName", "John");
            human2.put("lastName", null);
            assertThat(schema.shape(schemas).isValid(human2)).isFalse();

            Map<String, String> human3 = new HashMap<>();
            human3.put("firstName", "Anna");
            human3.put("lastName", "B");
            assertThat(schema.shape(schemas).isValid(human3)).isFalse();
        }

        @Test
        void testMultipleSchemas() {
            assertThat(schema.sizeof(1).sizeof(1).isValid(Map.of(KEY1, VALUE1))).isTrue();
            assertThat(schema.required().sizeof(1).sizeof(2).isValid(Map.of(KEY1, VALUE1, KEY2, VALUE2))).isTrue();
        }
    }
}
