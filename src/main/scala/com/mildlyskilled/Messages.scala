package com.mildlyskilled

sealed trait RenderMessage
case object Render extends RenderMessage
case class Render(start: Int, end: Int, scene: Scene) extends RenderMessage
case class Result(list: List[Pixel]) extends RenderMessage