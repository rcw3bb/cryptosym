package xyz.ronella.crypto.symmetric.gui.function;

import xyz.ronella.trivial.command.Invoker;

import java.util.StringJoiner;
import java.util.function.Supplier;

public class ApplicationTitle implements Supplier<String> {
    @Override
    public String get() {
        return new StringJoiner("")
                .add("CryptoSym GUI v")
                .add(Invoker.generate(new FullVersion()))
                .toString();
    }
}