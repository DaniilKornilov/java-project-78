package hexlet.code;

import hexlet.code.schema.MapSchema;
import hexlet.code.schema.NumberSchema;
import hexlet.code.schema.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void prepareValidator() {
        validator = new Validator();
    }

    @Nested
    class StringSchemaTest {
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
            assertThat(schema.minLength(10).isValid(null)).isTrue();
            assertThat(schema.minLength(10).isValid("Hexlet")).isFalse();
            assertThat(schema.minLength(10).isValid("what does the fox say")).isTrue();
        }

        @Test
        void testMultipleSchemas() {
            assertThat(schema.minLength(10).minLength(4).isValid("Hexlet")).isTrue();
            assertThat(schema.required().contains("what").minLength(10).isValid("what does the fox say")).isTrue();
        }
    }

    @Nested
    class NumberSchemaTest {
        private NumberSchema schema;

        @BeforeEach
        void prepareSchema() {
            schema = validator.number();
        }

        @Test
        void testWithoutSchema() {
            assertThat(schema.isValid(null)).isTrue();
            assertThat(schema.isValid(5)).isTrue();
        }

        @Test
        void testRequiredSchema() {
            assertThat(schema.required().isValid(null)).isFalse();
            assertThat(schema.required().isValid(10)).isTrue();
        }

        @Test
        void testPositiveSchema() {
            assertThat(schema.positive().isValid(null)).isTrue();
            assertThat(schema.positive().isValid(-10)).isFalse();
            assertThat(schema.positive().isValid(0)).isFalse();
            assertThat(schema.positive().isValid(100)).isTrue();
        }

        @Test
        void testRangeSchema() {
            assertThat(schema.range(5, 10).isValid(null)).isTrue();
            assertThat(schema.range(5, 10).isValid(5)).isTrue();
            assertThat(schema.range(5, 10).isValid(10)).isTrue();
            assertThat(schema.range(5, 10).isValid(4)).isFalse();
            assertThat(schema.range(5, 10).isValid(11)).isFalse();
        }

        @Test
        void testMultipleSchemas() {
            assertThat(schema.range(5, 10).range(1, 4).isValid(2)).isTrue();
            assertThat(schema.required().positive().range(1, 10).isValid(9)).isTrue();
        }
    }

    @Nested
    class MapSchemaTest {
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
        void testSize() {
            assertThat(schema.sizeof(2).isValid(null)).isTrue();
            assertThat(schema.sizeof(2).isValid(Map.of(1, 2))).isFalse();
            assertThat(schema.sizeof(2).isValid(Map.of(1, 2, 3, 4))).isTrue();
        }

        @Test
        void testMultipleSchemas() {
            assertThat(schema.sizeof(1).sizeof(1).isValid(Map.of(1, 2))).isTrue();
            assertThat(schema.required().sizeof(1).sizeof(2).isValid(Map.of(1, 2, 3, 4))).isTrue();
        }
    }
}
