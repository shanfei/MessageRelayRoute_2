package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import play.api.libs.json.JsString
import play.api.libs.json.JsArray


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the index page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Your new application is ready.")
      }
    }
    
    "return error object when MessageRelay " in {
       running(FakeApplication()) {
          val json = Json.obj(
        	 "email" -> JsString("test@example.com"),
             "password" -> JsString("fakepassword")
           )

			val req = FakeRequest("POST","/messageRelay").withJsonBody(json)
			
			val result = route(req).get
            println(contentAsString(result))
            status(result) must equalTo(OK)
			
       } 
    }
    
    
    "return normal object when MessageRelay " in {
       running(FakeApplication()) {
          val json = Json.obj(
        	 "message" -> "test@example.com",
             "recipients" -> Seq("1234567890","1234567890")
           )

			val req = FakeRequest("POST","/messageRelay").withJsonBody(json)
			
			val result = route(req).get
            println(contentAsString(result))
            status(result) must equalTo(OK)
			
       } 
    }
    
    
  }
}