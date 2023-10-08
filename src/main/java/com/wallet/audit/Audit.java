package com.wallet.audit;

public interface Audit {
    void log(String username, String action);
}
