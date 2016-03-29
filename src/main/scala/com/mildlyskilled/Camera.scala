package com.mildlyskilled

/**
  * Created by vladimirsivanovs on 29/03/2016.
  */
class Camera {
  val eye = Vector.origin
  val angle = 90f
  val frustum = (0.5 * angle * math.Pi / 180).toFloat

  val cosf = math.cos(frustum)
  val sinf = math.sin(frustum)

}
