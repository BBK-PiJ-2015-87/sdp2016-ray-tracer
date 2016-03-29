package com.mildlyskilled

import akka.actor.{Props, Actor}
import akka.routing.RoundRobinPool

/**
  * TODO
  * Make this an actor and write a message handler for at least the
  * set method.
  */

class Coordinator(scene: Scene, workers: Int, rowWidth: Int) extends Actor{

  def init(im: Image, of: String) = {
    image = im
    outfile = of
    waiting = im.width * im.height
  }

  val nodeRouter = context.actorOf(
    Props[RenderNode].withRouter(RoundRobinPool(workers)), name = "nodeRouter")

  def receive = {
    case Render =>
      for (i <- 0 until workers) nodeRouter ! Render(start, end) //send chunks of work to routers

    case Result(list) =>
      //add list to a whole image


    case Stop =>


  }

  // Number of pixels we're waiting for to be set.
  var waiting = 0
  var outfile: String = null
  var image: Image = null

  // TODO: make set a message
  def set(x: Int, y: Int, c: Colour) = {
    image(x, y) = c
    waiting -= 1
  }

  def print = {
    assert(waiting == 0)
    image.print(outfile)
  }

}