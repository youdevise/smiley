package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import elasticsearch.Repository
import org.joda.time.LocalDate
import play.Play

object Smilies extends Controller {

  def from(fromDate: String) = Action {
    println(fromDate)
    val data = repository().getSmileys(LocalDate.parse(fromDate))
    println(data)
//    val data = Map("Shaf" -> Map(
//        "2013-09-30" -> "sad",
//        "2013-10-01" -> "happy",
//        "2013-10-02" -> "neutral",
//        "2013-10-03" -> "neutral",
//        "2013-10-04" -> "happy",
//        "2013-10-08" -> "happy"
//        ),
//        "Tom W" -> Map(
//        "2013-10-01" -> "happy",
//        "2013-10-02" -> "neutral",
//        "2013-10-03" -> "neutral",
//        "2013-10-04" -> "sad",
//        "2013-10-08" -> "neutral"
//        ),
//        "Sergiusz" -> Map(
//        "2013-09-30" -> "happy",
//        "2013-10-01" -> "happy",
//        "2013-10-02" -> "neutral",
//        "2013-10-03" -> "neutral",
//        "2013-10-04" -> "happy",
//        "2013-10-08" -> "neutral"
//        )
//        )

    Ok(toJson(data))
  }

  def recordHappiness = Action { request =>
    val result = for {
      user <- request.getQueryString("user")
      date <- request.getQueryString("date")
      mood <- request.getQueryString("mood")
    } yield repository().recordHappiness(user, date, mood)
    result match {
      case Some(true) => Ok("Hello")
      case _ => BadRequest("Went wrong")
    }
  }
  
  def repository() : Repository = {
    val config = Play.application().configuration()
    val username = config.getString("es.username")
    val password = config.getString("es.password")
    val baseUrl = config.getString("es.baseUrl")
    new Repository(username, password, baseUrl)
  }
}
