package edu.drexel.ws.data

/**
 * Created by bsmitc on 11/2/14.
 */



import org.json4s._
import org.json4s.jackson._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization._


object DBMock {

  implicit val formats = DefaultFormats
  implicit val serializationFormats = Serialization.formats(NoTypeHints)

  //return a JValue that can be manipulated further
  private lazy val pubDBJson = {
    val jsonData  = getClass.getResourceAsStream("/pubs.json")
    val jstr = scala.io.Source.fromInputStream(jsonData).mkString

    parseJson(jstr)
  }

  //build a map so that pubID -> Publication object
  private lazy val pubDBMap = {
    val pubList = pubDBJson.extract[List[Publication]]
    pubList map (p => p.id -> p) toMap
  }

  def getAll = {
    compact(render(pubDBJson))
  }

  def getPub(idx:Integer) = {
    val thePub = pubDBMap.getOrElse(Some(idx),
                new Publication(error = Some(new PublicationError(500,"The Publication with index "+ idx + " is invalid"))))

    write(thePub)
  }
}
