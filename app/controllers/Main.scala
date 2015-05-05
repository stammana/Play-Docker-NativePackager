package controllers

/**
 * Created by stamma001c on 5/4/15.
 */

import scala.collection._
import scala.util.Random
object Main extends App {

  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  import WeekDay._

  def isWorkingDay(d: WeekDay) = ! (d == Sat || d == Sun)

  WeekDay.values filter isWorkingDay foreach println

  object Suit extends Enumeration {

    type Suit = Value

    val Diamond, Club, Hearts, Spades = Value

  }
  import Suit._
  case class CARD(suit :Suit,num : Int)

  //implicit order = scala.math.Ordering.

  val cards = mutable.ListBuffer[CARD]()

  cards += CARD(Suit.Diamond, 1)

  cards += CARD(Suit.Spades, 1)

  cards += CARD(Suit.Hearts,4)

  cards += CARD(Suit.Club,1)

  val sortedCards = cards.sortBy(card => (card.suit, card.num))

  val shuffledCards = Random.shuffle(sortedCards)

    sortedCards.foreach(println)
  println("--------------------------")
    shuffledCards.foreach(println)
}
