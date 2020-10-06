package org.falcion.avancode;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SideOnly(Side.SERVER)
public class Database {

    private MysqlDataSource dataSource;
    private ExecutorService executor;

    public Database(String host, String username, String password) {
        this.dataSource = new MysqlDataSource();
        this.dataSource.setURL(host);
        this.dataSource.setUser(username);
        this.dataSource.setPassword(password);

        this.executor = Executors.newSingleThreadExecutor();
    }

    public MysqlDataSource getDataSource() {
        return this.dataSource;
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    public boolean execute(String query, Object... params) {
        return false;
    }

    public PreparedStatement executeQuery(Connection connection,
                                          String query,
                                          Object... params
    ) {
        return null;
    }

    public void initTables() {}

    public void registerPlayer(String table, String name) {}

    public boolean isPlayerExists(String table, String name) {
        return false;
    }
}
