package co.touchlab.kampkit.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> SqlDriver.transactionWithContext(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> T,
): T = withContext(dispatcher) {
    transaction {
        block()
    }
} 