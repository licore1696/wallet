package com.wallet.storage;

import com.wallet.domain.Player;
import com.wallet.domain.Admin;

public interface Storage {
    Player getPlayer(String username);

    void addPlayer(Player player);

    void updatePlayer(Player player);

    Admin getAdmin(String username);

    void addAdmin(Admin admin);
}
