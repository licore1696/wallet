package com.wallet.storage;

import com.wallet.domain.Player;
import com.wallet.domain.Admin;
import java.util.List;

public interface Storage {
    Player getPlayer(String username);

    void addPlayer(Player player);

    void updatePlayer(Player player);

    Admin getAdmin(String username);

    void addAdmin(Admin admin);

    List<Player> getAllPlayers();
}
