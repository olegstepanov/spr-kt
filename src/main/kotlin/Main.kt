import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

object Application {
    fun swingMain() {
        val frame = JFrame()
        frame.size = Dimension(640, 480)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val imageCanvas = PainterCanvas()
        imageCanvas.painter = SixPetalRosette(10)::paint
        frame.add(imageCanvas)

        frame.isVisible = true
    }
}

fun main() {
    SwingUtilities.invokeAndWait(Application::swingMain)
}