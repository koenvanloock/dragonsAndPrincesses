package interpreters

import actors.DragonActor.{CapturePrincess, GiveMeThePrincess, KillTheBloodyWoman}
import actors.DragonCommand
import akka.util.Timeout
import akka.pattern.ask
import models.{FairyTale, Princess}
import scala.language.postfixOps
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object DragonFavoringInterpreter {
  type CaptureResponse = Either[String, Option[Princess]]
  implicit val timeout = Timeout(5 seconds)

  def tellAll(fairyTales: FairyTale*): Future[Seq[Unit]] = Future.sequence(fairyTales.map(tellTale))

  def tellTale(fairyTale: FairyTale): Future[Unit] = Future.sequence(fairyTale.commmands.map(runCommand(fairyTale))).map(_ => println(f"${fairyTale.name}%30s -- And they lived happily ever after..."))

  private def runCommand(fairyTale: FairyTale)(command: DragonCommand): Future[Unit] = command match {
    case KillTheBloodyWoman => fairyTale.dragon ! KillTheBloodyWoman; println(f"${fairyTale.name}%30s -- Die bloody woman"); Future(Unit)
    case CapturePrincess(aPrincess) => fairyTale.dragon ! CapturePrincess(fairyTale.princess) ; println(f"${fairyTale.name}%30s -- Our faboulous beast caught ${fairyTale.princess.name}"); Future(Unit)
    case GiveMeThePrincess => (fairyTale.dragon ? GiveMeThePrincess).mapTo[CaptureResponse].map(printPrincessResult(fairyTale))
  }

  private def printPrincessResult(fairyTale: FairyTale): (CaptureResponse) => Unit = {
    case Left(responseLine) => println(f"${fairyTale.name}%30s -- Our great dragon says: '$responseLine'")
    case Right(princessOpt) => println(f"${fairyTale.name}%30s -- Our great dragon says: '$princessOpt'")
  }
}
