package hexlet.code;

import hexlet.code.schema.NumberSchema;
import hexlet.code.schema.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        void testPositive() {
            assertThat(schema.positive().isValid(null)).isTrue();
            assertThat(schema.positive().isValid(-10)).isFalse();
            assertThat(schema.positive().isValid(0)).isFalse();
            assertThat(schema.positive().isValid(100)).isTrue();
        }

        @Test
        void testRange() {
            assertThat(schema.range(5, 10).isValid(null)).isTrue();
            assertThat(schema.range(5, 10).isValid(5)).isTrue();
            assertThat(schema.range(5, 10).isValid(10)).isTrue();
            assertThat(schema.range(5, 10).isValid(4)).isFalse();
            assertThat(schema.range(5, 10).isValid(11)).isFalse();
        }
    }
}
