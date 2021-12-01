package com.omkar.assignment1.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class LocalMessageCodec<T> implements MessageCodec <T,T>{

    private final String employeeDetails;

    public LocalMessageCodec(Class<T>type) {
        this.employeeDetails = type.getName();
    }

    @Override
    public void encodeToWire(Buffer buffer, Object o) {
        throw new UnsupportedOperationException("Only local encode is supported.");

    }

    @Override
    public T decodeFromWire(int i, Buffer buffer) {
        throw new UnsupportedOperationException("Only local encode is supported.");

    }

    @Override
    public T transform(final T t) {
        return t;
    }

    @Override
    public String name() {
        return this.employeeDetails;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
