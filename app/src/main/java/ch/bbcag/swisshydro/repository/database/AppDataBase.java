package ch.bbcag.swisshydro.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.bbcag.swisshydro.repository.database.dal.DataAgeDao;
import ch.bbcag.swisshydro.repository.database.dal.LocationDao;
import ch.bbcag.swisshydro.repository.database.objects.DataAge;
import ch.bbcag.swisshydro.repository.database.objects.Location;

@Database(
        entities = {
                DataAge.class,
                Location.class
        },
        version = 1
)
public abstract class AppDataBase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile AppDataBase INSTANCE;


    public static AppDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "appdatabase").allowMainThreadQueries().build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract DataAgeDao dataAgeDao();

    public abstract LocationDao locationDao();
}
