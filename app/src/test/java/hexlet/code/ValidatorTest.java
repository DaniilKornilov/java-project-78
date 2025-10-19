package hexlet.code;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {
    @Nested
    class StringSchemaTest {
        @Test
        void testWithoutSchema() {
            var v = new Validator();
            var schema = v.string();

            assertThat(schema.isValid("")).isTrue();
            assertThat(schema.isValid(null)).isTrue();
        }

        @Test
        void testRequiredSchema() {
            var v = new Validator();
            var schema = v.string();

            assertThat(schema.required().isValid(null)).isFalse();
            assertThat(schema.required().isValid("")).isFalse();
            assertThat(schema.required().isValid("what does the fox say")).isTrue();
            assertThat(schema.required().isValid("hexlet")).isTrue();
        }

        @Test
        void testContainsSchema() {
            var v = new Validator();
            var schema = v.string();

            assertThat(schema.contains("wh").isValid("what does the fox say")).isTrue();
            assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();
            assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();
        }

        @Test
        void testMultipleSchemas() {
            var v = new Validator();
            var schema = v.string();

            assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();
            assertThat(schema.minLength(10).minLength(4).isValid("Hexlet")).isTrue();
        }
    }
}
