import java.io.File

import akka.actor.{Props, ActorSystem}
import com.mildlyskilled.{Scene, Image, Coordinator, Trace}

object Tracer extends App {

  val (infile, outfile) = ("src/main/resources/input.dat", "output.png")
  val scene = Scene.fromFile(infile)
  val t = scene.getTrace()
  render(scene, outfile, t.Width, t.Height)

  println("rays cast " + t.rayCount)
  println("rays hit " + t.hitCount)
  println("light " + t.lightCount)
  println("dark " + t.darkCount)

  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    // Create an Akka system
    val system = ActorSystem("PiSystem")

    // create the result listener, which will print the result and shutdown the system
    val coordinator = system.actorOf(Props[Coordinator], name = "Coordinator")

//    // create the master
//    val master = system.actorOf(Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener)),
//      name = "master")

    // start the calculation
    coordinator ! Render
    val image = new Image(width, height)

    // Init the coordinator -- must be done before starting it.
    Coordinator.init(image, outfile)

    // TODO: Start the Coordinator actor.

    scene.traceImage(width, height)

    // TODO:
    // This one is tricky--we can't simply send a message here to print
    // the image, since the actors started by traceImage haven't necessarily
    // finished yet.  Maybe print should be called elsewhere?
    Coordinator.print
  }

}
