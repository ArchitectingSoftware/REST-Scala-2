package edu.drexel.ws.service

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.routing._


import edu.drexel.ws.data._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class PublicationServiceActor extends Actor with PublicationService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait PublicationService extends HttpService {

  val myRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>WELCOME TO MY PUBLICATION WEB SERVICE</h1>
                <p>You can access the following services:</p>
                <p>/publications to get all pubs</p>
                <p>/publications/:id to get a particular pub</p>
              </body>
            </html>
          }
        }
      }
    } ~
    path("publications") {
      get{
        respondWithMediaType(`application/json`){
          complete{
            DBMock.getAll
          }
        }
      }
    }~
      path("publications" / IntNumber) { pubId =>
        get{
          respondWithMediaType(`application/json`){
            complete{
              DBMock.getPub(pubId)
            }
          }
        }
      }

}