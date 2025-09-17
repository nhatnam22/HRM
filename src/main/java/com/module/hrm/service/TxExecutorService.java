package com.module.hrm.service;

import java.util.function.Supplier;

public interface TxExecutorService {
    <T> T execute(Supplier<T> action);

    void execute(Runnable action);

    <T> T executeInNewTx(Supplier<T> action);

    void executeInNewTx(Runnable action);
}
