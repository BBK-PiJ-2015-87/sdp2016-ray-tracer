package com.mildlyskilled

sealed trait RenderMessage
case object Render extends RenderMessage
case class Render(start: Int, nrOfElements: Int) extends RenderMessage
case class Result(x: Double, y: Double, c:Colour) extends RenderMessage