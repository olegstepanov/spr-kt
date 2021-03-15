import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SixPetalRosette {
    var color = Color.WHITE

    private fun drawArc(g: Graphics2D, size: Int) {
        val a = size / 2
        val r = (a / sin(Math.PI / 6)).toInt()
        val h = (r * cos(Math.PI / 6)).toInt()

        g.drawArc(
            -r, -h - r,
            2 * r, 2 * r,
            -60,
            -60
        )
    }

    private fun drawPetal(g: Graphics2D, x1: Int, y1: Int, x2: Int, y2: Int) {
        val petalSize = sqrt((x1 - x2).toDouble() * (x1 - x2) + (y1 - y2) * (y1 - y2))
        val theta = acos((x2 - x1) / petalSize)

        g.translate(x1, y1)
        g.transform(AffineTransform.getRotateInstance(-theta))
        g.translate(petalSize / 2, 0.0)
        drawArc(g, petalSize.toInt())
        g.transform(AffineTransform.getScaleInstance(1.0, -1.0))
        drawArc(g, petalSize.toInt())
        g.transform(AffineTransform.getScaleInstance(1.0, -1.0))
        g.translate(-petalSize / 2, 0.0)
        g.transform(AffineTransform.getRotateInstance(theta))
        g.translate(-x1, -y1)
    }

    fun paint(g: Graphics2D, bounds: Rectangle) {
        val petalSize = bounds.height / 2

        g.color = color

        g.translate(bounds.centerX, bounds.centerY)

        for (i in 1..6) {
            drawPetal(g, 0, 0, 0, -petalSize)
            g.translate(0, -petalSize)
            g.transform(AffineTransform.getRotateInstance(Math.PI / 6))
            drawPetal(g, 0, 0, petalSize, 0)
            g.transform(AffineTransform.getRotateInstance(-Math.PI / 6))
            g.translate(0, petalSize)
            g.transform(AffineTransform.getRotateInstance(Math.PI / 3))
        }
    }
}