package models

import play.api.libs.json._
import scala.collection.mutable.ListBuffer
import play.api.libs.json.JsSuccess
import scala.Some


object Input {
  implicit def readInputs = Json.reads[Input]
  implicit def writes = Json.writes[Input] 
  
}


object Output {
     implicit def readNetRoutes = Json.reads[NetRoute]
     implicit def writeNetRoutes = Json.writes[NetRoute]
     implicit def readOutputs = Json.reads[Output]
     implicit def writeOutputs = Json.writes[Output]
     
}

object OutputError {
     implicit def writeErrors = Json.writes[OutputError]
     implicit def readErrors = Json.reads[OutputError]
}

case class Input(message:String, telNumbers:ListBuffer[String])

case class NetRoute(ip:String,recipients:ListBuffer[String])

sealed trait OutputT 

case class Output(message:String,routes:ListBuffer[NetRoute]) extends OutputT

case class OutputError(code:Int,message:String,reason:String) extends OutputT 

