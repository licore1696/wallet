package com.wallet.storage;

import com.wallet.domain.Player;

public interface Storage {
    Player getPlayer(String username);

    void addPlayer(Player player);

    void updatePlayer(Player player);
}
