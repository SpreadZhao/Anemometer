import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import test.TestScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        TestScreen()
    }
}
