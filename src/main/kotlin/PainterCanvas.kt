import java.awt.*

class PainterCanvas : Canvas() {
    var painter: Painter = { _, _ -> }

    override fun paint(g: Graphics) {
        super.paint(g)

        g.color = Color.BLACK
        g.fillRect(0, 0, width, height)
        painter(g as Graphics2D, Rectangle(0, 0, width, height))
    }
}