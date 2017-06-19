package powerdms.forkspoon.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import powerdms.forkspoon.helper.log.LogManager;
import powerdms.forkspoon.helper.manager.ConfigurationManager;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;


public class AppDatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "powerdms.db";
    private static final int DATABASE_VERSION = 10;

    public AppDatabaseHelper(Context context) {
        super(context, ConfigurationManager.getInstance().getPath(3) + DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ZomatoRestaurant.class);
        } catch (SQLException e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            //Removing
            TableUtils.dropTable(connectionSource, ZomatoRestaurant.class, true);

            //Creating
            TableUtils.createTable(connectionSource, ZomatoRestaurant.class);

        } catch (SQLException e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
