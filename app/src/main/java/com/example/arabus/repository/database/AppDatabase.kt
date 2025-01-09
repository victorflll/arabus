import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arabus.repository.internal.entities.Accessibility
import com.example.arabus.repository.internal.entities.Address
import com.example.arabus.repository.internal.entities.Bus
import com.example.arabus.repository.internal.entities.Favorite
import com.example.arabus.repository.internal.entities.Feedback
import com.example.arabus.repository.internal.entities.History
import com.example.arabus.repository.internal.entities.Profile
import com.example.arabus.repository.internal.entities.Role
import com.example.arabus.repository.internal.entities.Route
import com.example.arabus.repository.internal.entities.User

@Database(
    entities = [
        User::class,
        Profile::class,
        Address::class,
        Role::class,
        Bus::class,
        Route::class,
        Favorite::class,
        History::class,
        Accessibility::class,
        Feedback::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun profileDao(): ProfileDao
    abstract fun addressDao(): AddressDao
    abstract fun roleDao(): RoleDao
    abstract fun busDao(): BusDao
    abstract fun routeDao(): RouteDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao
    abstract fun accessibilityDao(): AccessibilityDao
    abstract fun feedbackDao(): FeedbackDao
}
