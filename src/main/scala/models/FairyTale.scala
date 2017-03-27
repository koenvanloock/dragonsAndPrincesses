package models

import actors.DragonCommand
import akka.actor.ActorRef


case class FairyTale(name: String, dragon: ActorRef, princess: Princess, commmands: List[DragonCommand])
