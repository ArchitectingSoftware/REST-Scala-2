package edu.drexel.ws.data

/**
  * Created by brian on 11/11/15.
  */
case class PubResults (
                      results: Array[Publication],
                      metadata: MetaData
                      )

case class MetaData(
                   count : Int,
                   error : Boolean,
                   statusCode: Int,
                   message: String = ""
                   )
