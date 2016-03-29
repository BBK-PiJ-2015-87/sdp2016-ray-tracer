package com.mildlyskilled

import akka.actor._

class RenderNode extends Actor {

  val width;

  def receive = {
    case Render(start, rows) => sender ! Result(RenderRows(start, rows)) // perform the work
  }

  def RenderRows(start: Int, rows: Int): List[Pixel] = {
    var acc = List()
    for (i <- start until (start + rows)){

    }

    acc
  }
}
