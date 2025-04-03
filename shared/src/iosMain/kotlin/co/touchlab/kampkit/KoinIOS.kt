package co.touchlab.kampkit

import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kermit.Logger
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

internal fun initKoinIos(
    userDefaults: NSUserDefaults,
    appInfo: AppInfo,
    doOnStartup: () -> Unit,
): KoinApplication =
    initKoin(
        module {
            single<Settings> { NSUserDefaultsSettings(userDefaults) }
            single { appInfo }
            single { doOnStartup }
        },
    )

internal actual val platformModule =
    module {
        single { Darwin.create() }
        single { BreedViewModel(get(), getWith("BreedViewModel")) }
    }

// Access from Swift to create a logger
@Suppress("unused")
internal fun Koin.loggerWithTag(tag: String) = get<Logger>(qualifier = null) { parametersOf(tag) }

@Suppress("unused") // Called from Swift
internal object KotlinDependencies : KoinComponent {
    fun getBreedViewModel() = getKoin().get<BreedViewModel>()
}
