package co.alexdev.bitsbake.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;

@Database(entities = {Recipe.class, Ingredients.class, Steps.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "BITSBAKE_DATABASE";
    private static final Object LOCK = new Object();
    private static RecipeDatabase sInstance;

    public static RecipeDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
}
