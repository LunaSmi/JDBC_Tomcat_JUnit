package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PropertiesUtilTest {

    @ParameterizedTest
    @MethodSource("getPropertyArguments")
    void checkGet(String key, String expectedValue){
        String actualValue = PropertiesUtil.getProperty(key);
        assert actualValue.equals(expectedValue);
    }

    static Stream<Arguments> getPropertyArguments() {
        return Stream.of(
                Arguments.of("db.username","dbuser"),
                Arguments.of("db.url","jdbc:postgresql://localhost:5432/astonDB"),
                Arguments.of("db.driver","org.postgresql.Driver")
        );
    }

}
