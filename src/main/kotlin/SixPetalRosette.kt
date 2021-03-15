import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class SixPetalRosette(val petalsNum: Int) {
    var color = Color.WHITE

    companion object {
        private val PETAL_WIDTH_TO_ROSETTE_SIZE_RATIO = 0.25 / sin(Math.PI / 6) * (1 - cos(Math.PI / 6)) * 2
    }

    fun paint(g: Graphics2D, bounds: Rectangle) {
        g.color = color
        val viewportBorder = 5
        val viewportWidth = bounds.width - viewportBorder * 2
        val rosetteSize =
            (viewportWidth / (petalsNum * (1 - PETAL_WIDTH_TO_ROSETTE_SIZE_RATIO) + PETAL_WIDTH_TO_ROSETTE_SIZE_RATIO)).toInt()
        val petalWidth = rosetteSize * PETAL_WIDTH_TO_ROSETTE_SIZE_RATIO + 1
        for (j in 0..(petalsNum - 1)) {
            val startX = rosetteSize / 2.0 + viewportBorder/* + j * (rosetteSize - petalWidth) / 2*/
            val y = bounds.centerY - j * rosetteSize / 2
            for (i in 0 until petalsNum) {
                val centerX = startX + i * (rosetteSize - petalWidth)
                drawRosette(g, centerX, y, rosetteSize)
            }
        }
    }

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

    private fun drawRosette(g: Graphics2D, centerX: Double, centerY: Double, rosetteSize: Int) {
        g.translate(centerX, centerY)

        val petalSize = rosetteSize / 2

        for (i in 1..6) {
            drawPetal(g, 0, 0, 0, -petalSize)
            g.translate(0, -petalSize)
            g.transform(AffineTransform.getRotateInstance(Math.PI / 6))
            drawPetal(g, 0, 0, petalSize, 0)
            g.transform(AffineTransform.getRotateInstance(-Math.PI / 6))
            g.translate(0, petalSize)
            g.transform(AffineTransform.getRotateInstance(Math.PI / 3))
        }

        g.translate(-centerX, -centerY)
    }
}