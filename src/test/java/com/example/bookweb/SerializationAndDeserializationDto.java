package com.example.bookweb;

import java.nio.file.Paths;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class SerializationAndDeserializationDto<T> {

    private final ObjectMapper mapper = new ObjectMapper();

    protected abstract T createDto();

    protected abstract void validateDtoFields(T dto);

    @TestFactory
    Stream<DynamicTest> testSerializationAndDeserialization() {
        return Stream.of(
                DynamicTest.dynamicTest("testSerialization", () -> {
                    final var jsonFile = Paths.get("src/test/resources/json/" + createDto().getClass().getSimpleName() + "TestSerialization.json").toFile();
                    final var expected = mapper.readTree(jsonFile).toString();
                    final var actual = mapper.writeValueAsString(createDto());

                    Assertions.assertThat(actual).isEqualTo(expected);
                }),

                DynamicTest.dynamicTest("testDeserialization", () -> {
                    final var jsonFile = Paths.get("src/test/resources/json/" + createDto().getClass().getSimpleName() + "TestDeserialization.json").toFile();
                    final var actual = (T) mapper.readValue(jsonFile, createDto().getClass());

                    validateDtoFields(actual);
                }));
    }

}
