package it.vfsfitvnm.vimusic.facedetector

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
//import android.graphics.Path
import android.graphics.PointF
import android.graphics.Rect
import com.google.mlkit.vision.face.Face

class FaceBox(
    overlay: FaceBoxOverlay,
    private val face: Face,
    private val imageRect: Rect,
    private val landmarks: List<PointF>,
    private val contours: List<MutableList<PointF>>

) : FaceBoxOverlay.FaceBox(overlay) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 6.0f
    }

    private val rect = getBoxRect(
        imageRectWidth = imageRect.width().toFloat(),
        imageRectHeight = imageRect.height().toFloat(),
        faceBoundingBox = face.boundingBox
    )

    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(rect, paint)
        drawLandmarksAndContours(canvas)
    }

    private fun drawLandmarksAndContours(canvas: Canvas?) {
        val paint = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL
            strokeWidth = 5.0f
        }

        val paint1 = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            strokeWidth = 5.0f
        }

        val scale = overlay.mScale ?: 1.0f
        val offsetX = overlay.mOffsetX ?: 0.0f
        val offsetY = overlay.mOffsetY ?: 0.0f

        /*for (landmark in landmarks) {
            canvas?.drawCircle(
                overlay.width - (landmark.x * scale + offsetX),
                landmark.y * scale + offsetY,
                5.0f,
                paint1
            )
        }*/

        for (contour in contours) {
            for (point in contour!!) {
                canvas?.drawCircle(
                    overlay.width - (point.x * scale + offsetX),
                    point.y * scale + offsetY,
                    5.0f,
                    paint
                )
            }
        }
    }
}