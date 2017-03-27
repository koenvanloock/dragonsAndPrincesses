package actors

import akka.actor.{Actor, Props}
import models.Princess

sealed trait DragonCommand

class DragonActor extends Actor{
  import DragonActor._
  private var capturedPrincess: Option[Princess] = None

  override def receive: Receive = {
    case CapturePrincess(princess) => if(capturedPrincess.nonEmpty) sender() ! "I already have one!" else capturedPrincess = Some(princess)
    case GiveMeThePrincess         => if(capturedPrincess.nonEmpty) sender() ! Right(capturedPrincess) else sender() ! Left("I don't have a princess.")
    case KillTheBloodyWoman         => if(capturedPrincess.nonEmpty) {
                                          println(s"Die ${capturedPrincess.get.name}!!!")
                                          capturedPrincess = None
                                        }
  }
}


object DragonActor {
  def props = Props[DragonActor]

  case class CapturePrincess(princess: Princess) extends DragonCommand
  case object GiveMeThePrincess extends DragonCommand
  case object KillTheBloodyWoman extends DragonCommand

}
