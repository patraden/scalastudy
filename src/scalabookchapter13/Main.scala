package scalabookchapter13

object Main extends App {

  /**
   * One way:
   */
//  package bobsrockets.navigation
//  class Navigator

  /**
   * Another way:
   */
//    package bobsrockets.navigation {
//      class Navigator
//  }

  /**
   * Third way:
   */
//  package bobsrockets {
//    package navigation {
//      // in package bobsrockets.navigation
//      class Navigator
//      package tests {
//        // in package bobsrockets.navigation.tests
//        class NavigatorSuite
//      }
//    }
//  }

  /**
   * Package hierarchy helps to inform compiler that packages are interconnected and
   * have some relevance links.
   */
//  package bobsrockets {
//    package navigation {
//      // in package bobsrockets.navigation
//      class Navigator {
//        // Do not need to specify full name like bobsrockets.navigation.StartMap
//        val map = new StarMap
//      }
//      class StarMap
//    }
//    class Ship {
//      // Do not need to specify full name like bobsrockets.navigation.Navigator
//      val nav = new navigation.Navigator
//    }
//    package fleets {
//      class Fleet {
//        // Do not need to specify full name like bobsrockets.Ship
//        def addShip() = {new Ship}
//      }
//    }
//  }

  /**
   * Another syntax to specify package hierarchy.
   */
//  package bobsrockets {
//    class Ship
//  }
//  package bobsrockets
//  package fleets
//  class Fleet {
//    // Do not need to specify bobsrockets.Ship
//    def addShip() =  { new Ship }
//  }

  /**
   * IMPORTANT:
   * Sometime visible namespace might have too much and package names might hide each others.
   * Below code demonstrates 3 packages with name "launch" at various levels.
   * The question is how to related to Booster1, Booster2, Booster3?
   * Thus Booster1 would be easiest as it is the closest to the visible area for compiler.
   * Booster2 would be accessible very in a simple way through full package hierarchy.
   * And finally Booster3 access can be gathered through so called "_root_" package.
   * "_root_" is the special and the highest package in the hierarchy.
   */
//  package launch {
//    class Booster3
//  }
//  package bobsrockets {
//    package navigation {
//      package launch {
//        class booster1
//      }
//      class MissionControl {
//        val booster1 = new launch.Booster1
//        val booster2 = new bobsrockets.launch.Booster2
//        val booster3 = new _root_.launch.Booster3
//      }
//    }
//    package launch {
//      class Booster2
//    }
//  }

  /**
   * Overall imports in scala have the following 3 properties:
   * 1. Imports in Scala can be everywhere in code.
   * 2. Except packages can be linked to objects (singletons and or class instances).
   * 3. Allows to change imported names or hide some of them.
   */

  import myclasses.bobsdelights.Fruit
  import myclasses.bobsdelights._
  import myclasses.bobsdelights.Fruits._

  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are " + color)
  }

  import java.util.regex
  class AStarB {
    // correspondence to java.util.regex.Pattern
    val pat = regex.Pattern.compile("a*b")
  }

  import Fruits.{Apple, Orange} // import of 2 objects
  import Fruits.{Apple => McIntosh, Orange} // import of two objects with one renamed
  import java.sql.{Date => SDate} // Import of class renamed
  import java.{sql => S} // import of package renamed
  import Fruits._ // import of all members of Fruits
  import Fruits.{Apple => McIntosh, _} // import of all members of Fruits with Apple renamed
  import Fruits.{Pear => _, _} // import of all objects in Fruits EXCEPT Pear
  // <original_name> => _ - excepts <original_name> in import
  // in a way renaming of anything to _ means hiding

  // if we want to import Apple from Notebooks, but not from Fruits:
//  import Notebooks._
//  import Fruits.{Apple => _, _}

  /**
   * Implicit imports:
   * By default Scala implicitly does the following imports to any program
   */

  import java.lang // everything from package java.lang
  import scala._ // everything from package scala
  import Predef._ // everything from package Predef

  /**
   * Access modification.
   * private and protected
   * Scala does not have public members:
   * everything which is not marked as private or protected is public.
   */

  /**
   * private member is visible inside the object containing its definition.
   * This also applies to inner classes. See example:
   */

  class Outer {
    class Inner {
      private def f() = { println("f") }
      class InnerMost {
        f() // is allowed!
      }
    }
//    (new Inner).f() // will throw an error
  }

  /**
   * Access to protected members in scala is less free compared to Java.
   * protected members in Scala accessible through subclasses of class where member was defined only.
   */

//  package p {
//    class Super {
//      protected def f() = { println("f") }
//
//    }
//    class Sub extends Super {
//      f()
//    }
//    class Other {
//      (new Super).f() // will cause an error
//    }
//  }

  /**
   * Access modifiers in scala can be enhanced by specificators!
   * private[X] and protected[X]
   * which defines Package, Class or Singleton.
   */
//  package bobsrockets
//  package navigation {
//    private[bobsrockets] class Navigator {
//      protected[navigation] def useStarChart() = {}
//      class LegOfJourney {
//        private[Navigator] val distance = 100
//      }
//      private[this] var speed = 200
//    }
//  }
//  package launch {
//    import navigation._
//    object Vehicle {
//      private[launch] val guide = New Navigator
//    }
//  }

  /**
   * Access between Class and its companion-object.
   * Simple and very special rule here:
   * Class shares full access to its companion-object and vise-versa!
   * In particular, object might access any private members of its companion Class,
   * as well as Class can access to all private members of the object.
   */

  class Rocket {
    import Rocket.fuel
    private def canGoHomeAgain = fuel > 20
  }

  object Rocket {
    private def fuel = 10
    def chooseStrategy(rocket: Rocket) = {
      if (rocket.canGoHomeAgain)
        goHome()
      else
        pickAStar()
    }
    def goHome() = {}
    def pickAStar() = {}
  }


  /**
   * Finally few words about package objects.
   * If we need some helpful methods to be available for entire package,
   * we can use package objetcs.
   * Often used for type aliases subjected to a package and
   * implicit conversions.
   * Thus package scala has its own package object with some definitions
   * available in Scala.
   * Package objects compiled into package.class in catalog package.
   */


//  package object bobsdelights {
//    def showFruit(fruit: Fruit) = {
//      import fruit._
//      println(name + "s are " + color)
//    }
//  }
//
//  package printmenu
//  import bobsdelights.Fruits
//  import bobsdelights.showFruit
//
//  object PrintMenu {
//    def main(args: Array[String]) ={
//      for (fruit <- Fruits.menu) {
//        showFruit(fruit)
//      }
//    }
//  }

}
