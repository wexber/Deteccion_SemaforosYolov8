package com.surendramaran.yolov8tflite

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.util.LinkedList
import kotlin.math.max

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results = listOf<BoundingBox>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()
    private lateinit var mainActivity: MainActivity
    private var bounds = Rect()

    init {
        initPaints()
    }
    private val paint = Paint().apply {
        color = android.graphics.Color.RED
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }
    private var boundingBoxes: List<BoundingBox> = listOf()
    fun clear() {
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        boundingBoxes = emptyList()
        results = listOf() // Limpiar la lista de resultados
        invalidate() // Invalidar la vista para que se redibuje
        initPaints()
    }
    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }
/*
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        boundingBoxes.forEach { box ->
            val left = box.x1 * width
            val top = box.y1 * height
            val right = box.x2 * width
            val bottom = box.y2 * height

            canvas.drawRect(left, top, right, bottom, paint)
            val drawableText = box.clsName // Obtener el nombre del objeto del objeto BoundingBox

            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)
        }
    }
*/

    // Método para configurar la referencia a MainActivity
    fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    // Llamar a la función speak con el nombre del objeto
    private fun speak(objectName: String) {
        mainActivity.speak(objectName)
    }
override fun draw(canvas: Canvas) {
    super.draw(canvas)

    boundingBoxes.forEach { box ->
        val left = box.x1 * width
        val top = box.y1 * height
        val right = box.x2 * width
        val bottom = box.y2 * height

        canvas.drawRect(left, top, right, bottom, paint)
        val drawableText = box.clsName // Obtener el nombre del objeto del objeto BoundingBox

        // Dibujar el nombre del objeto
        textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
        val textWidth = bounds.width()
        val textHeight = bounds.height()
        canvas.drawRect(
            left,
            top,
            left + textWidth + BOUNDING_RECT_TEXT_PADDING,
            top + textHeight + BOUNDING_RECT_TEXT_PADDING,
            textBackgroundPaint
        )
        canvas.drawText(drawableText, left, top + bounds.height(), textPaint)

        // Llamar a la función speak con el nombre del objeto
        speak(drawableText)
    }
}
    fun setResults(results: List<BoundingBox>) {
        boundingBoxes = results
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        boundingBoxes.forEach {
            canvas.drawRect(it.x1 * width, it.y1 * height, it.x2 * width, it.y2 * height, paint)
        }
    }
    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}