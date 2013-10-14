package models

import scala.collection.mutable.{ListBuffer, Queue, LinkedList}
import play.api.libs.json._
import play.api.libs.json.JsSuccess
import scala.Some

class MessageRelayCategory(category_p:String,relaySubNets_p:String,messageThroughput_p:Int,cost_p:Double) {
   val category =  category_p
   val relaySubNet = relaySubNets_p
   val messageThroughput = messageThroughput_p
   val cost = cost_p
   val subNetNumbers = 254
}



object dispatcher {

    val messageRelayCategories = List(
                                      new MessageRelayCategory("SUPER","10.0.4.0/24",25,0.25),
                                      new MessageRelayCategory("LARGE","10.0.3.0/24",10,0.10),
                                      new MessageRelayCategory("MEDIUM","10.0.2.0/24",5,0.05),
                                      new MessageRelayCategory("SMALL","10.0.1.0/24",1,0.01)
                                    )


    def dispatch(input_p:Option[Input]):OutputT = {

        if (input_p == None) {
          return OutputError(1,"request input is Wrong","request input is Wrong")
        }

        val input = input_p.get
        val output:Output = Output(input.message,ListBuffer[NetRoute]())


        var telNumbers = input.recipients.size

        messageRelayCategories.foreach(
            {  messageRelayCategory =>

               val neededSubNetNumbers =  telNumbers / messageRelayCategory.messageThroughput
               if (neededSubNetNumbers > 0) {
                    telNumbers = telNumbers % messageRelayCategory.messageThroughput

                    //build output
                    val recipients = ListBuffer[String]();
                    for (i <- 0 until (neededSubNetNumbers * messageRelayCategory.messageThroughput)) {
                      recipients += input.recipients.remove(0)
                    }


                    val route = new NetRoute(messageRelayCategory.relaySubNet.replace("0/24","1")
                      ,recipients)
                    output.routes += route
               }
            }
        )

        output
    }
}

