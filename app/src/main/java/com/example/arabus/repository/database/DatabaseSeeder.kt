import com.example.arabus.repository.database.AppDatabase
import com.example.arabus.repository.internal.entities.Role
import com.example.arabus.repository.internal.entities.User
import com.example.arabus.utils.PasswordUtil
import java.util.Date

object DatabaseSeeder {
    suspend fun populateDatabase(db: AppDatabase) {
        val roleDao = db.roleDao()
        val userDao = db.userDao()

        val roles = listOf(
            Role(name = "Admin"),
            Role(name = "User"),
            Role(name = "Driver"),
        )
        roles.forEach { role ->
            roleDao.insert(role)
        }

        val users = listOf(
            User(
                email = "admin@gmail.com",
                password = PasswordUtil.hashPassword("admin123"),
                roleId = 1,
                createdAt = Date()
            ),
            User(
                email = "user@gmail.com",
                password = PasswordUtil.hashPassword("user123"),
                roleId = 2,
                createdAt = Date()
            )
        )
        users.forEach { user ->
            userDao.insert(user)
        }
    }
}