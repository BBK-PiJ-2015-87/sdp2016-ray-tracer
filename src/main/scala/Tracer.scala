import akka.actor.{Props, ActorSystem}
import com.mildlyskilled._

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
    val system = ActorSystem("RenderSystem")
    val coordinator = system.actorOf(Props(new Coordinator(scene, 10, 10)), name = "Coordinator")
    val image = new Image(width, height)

    // TODO: Start the Coordinator actor.
    coordinator ! Render

    // TODO:
    // This one is tricky--we can't simply send a message here to print
    // the image, since the actors started by traceImage haven't necessarily
    // finished yet.  Maybe print should be called elsewhere?
    coordinator.print
  }

}
