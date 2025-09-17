package com.module.hrm.service.impl;

import com.module.hrm.service.TxExecutorService;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxExecutorServiceImpl implements TxExecutorService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T execute(Supplier<T> action) {
        return action.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(Runnable action) {
        action.run();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T executeInNewTx(Supplier<T> action) {
        return action.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executeInNewTx(Runnable action) {
        action.run();
    }
}
