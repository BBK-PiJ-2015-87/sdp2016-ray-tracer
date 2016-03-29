package com.mildlyskilled

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import akka.routing.RoundRobinPool

/**
  * TODO
  * Make this an actor and write a message handler for at least the
  * set method.
  */

object Coordinator extends Actor{
  def init(im: Image, of: String) = {
    image = im
    outfile = of
    waiting = im.width * im.height
  }

  val workerRouter = context.actorOf(
    Props[RenderNode].withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")

  def receive = {
    case Calculate ⇒
      for (i ← 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)

    case Result(value) ⇒
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        // Send the result to the listener
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start) millis)
        // Stops this actor and all its supervised children
        context stop self
      }
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

  override def receive: Receive = ???
}