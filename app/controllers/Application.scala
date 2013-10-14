package controllers

import play.api._
import play.api.mvc._
import models.{dispatcher, Input, Output, OutputError}
import play.api.libs.json
import play.api.libs.json.Json

object Application extends Controller {
  
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def messageRelay = Action(parse.json) { request =>
     //read json
     val jsonRequest = request.body
    
     val input =  jsonRequest.asOpt[Input]
     val output = dispatcher.dispatch(input)
     
     output match {
       case Output(message,routes) =>  Ok(Json.toJson(output.asInstanceOf[Output]))
       case OutputError(code,messsage,reason) => Ok(Json.toJson(output.asInstanceOf[OutputError]))
       case _ => Results.BadRequest
     }
     
  }
  
}