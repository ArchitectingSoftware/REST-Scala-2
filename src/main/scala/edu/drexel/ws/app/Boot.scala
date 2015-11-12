package edu.drexel.ws.app

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.{ConfigFactory, Config}
import edu.drexel.ws.service.PublicationServiceActor
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[PublicationServiceActor], "publication-rest-scala-service")

  val config = ConfigFactory.load()
  val appConfig = config.getConfig("application")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  //IO(Http) ? Http.Bind(service, interface="localhost", port = appConfig.getInt("port"))

  IO(Http) ? Http.Bind(service, interface = appConfig.getString("host"), port = appConfig.getInt("port"))
}
