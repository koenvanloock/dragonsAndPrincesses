import actors.DragonActor
import akka.actor.ActorSystem
import akka.util.Timeout

import scala.concurrent.duration._
import actors.DragonActor._
import interpreters.{DragonFavoringInterpreter, PrincessLovingInterpreter}
import models.{FairyTale, Princess}

object QuestRunner {

  def main(args: Array[String]): Unit = {
    implicit val timeout = Timeout(5 seconds)

    val actorsystem = ActorSystem.create()
    val dragon = actorsystem.actorOf(DragonActor.props, "dragonActor")

    val snowWhite = FairyTale("Snowwhite", dragon,Princess("Snowwhite"),List(
      CapturePrincess(Princess("Snowwhite")),
        GiveMeThePrincess
    ))

    val sleepingBeauty = FairyTale("Sleeping beauty", dragon,Princess("sleeping beauty"),List(
      CapturePrincess(Princess("Sleeping beauty")),
      KillTheBloodyWoman,
      GiveMeThePrincess
    ))

    DragonFavoringInterpreter.tellAll(snowWhite, sleepingBeauty)

    Thread.sleep(5000)
    actorsystem.terminate()
  }
}
