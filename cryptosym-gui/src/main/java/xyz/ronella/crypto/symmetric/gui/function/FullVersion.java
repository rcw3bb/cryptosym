package xyz.ronella.crypto.symmetric.gui.function;

import xyz.ronella.crypto.symmetric.gui.common.impl.Version;
import xyz.ronella.trivial.decorator.StringBuilderAppender;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Supplier;

import static java.util.function.Predicate.not;

public class FullVersion implements Supplier<String> {
    @Override
    public String get() {
        final var version = new Version();

        final var buildVersion = Optional.of(version.getBuild()).filter(not(String::isBlank)).orElse("GA");

        final var fullVersion = new StringJoiner(".")
                .add(version.getMajor().toString())
                .add(version.getMinor().toString())
                .add(version.getMaintenance())
                .toString();

        return fullVersion + "-" + buildVersion;
    }
}