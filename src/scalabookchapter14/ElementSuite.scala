package scalabookchapter14

  /**
   * Predef.assert and Predef.ensuring can be enabled/disabled through JVM commands -ea and -da.
   * Overall Scala has many tools for testing.
   * Stating with Java tools like JUnit, TestNG and finishing with Scala ones,
   * like ScalaTest, specs2 and ScalaCheck.
   * ScalaTest though is the most flexible tool.
   */

import org.scalatest.funsuite.AnyFunSuite
import myclasses.Element.elem

class ElementSuite extends AnyFunSuite {
  test("elem result should have passed width") {
    val ele = elem('x', 2, 3)
//    assert(ele.width == 2)
    assertResult(2){ ele.width }
//    assertThrows[IllegalArgumentException] {
//      elem('x', 2, 3)
//    }
    val caught = intercept[ArithmeticException]{
      1 / 0
    }
    assert(caught.getMessage == "/ by zero")
  }
}


/**
 * BDD style supporting tests.
 */

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ElementSpec extends AnyFlatSpec with Matchers {
  "A UniformElement" should "have a width equal to the passed value" in {
    val ele = elem('x', 2, 3)
    ele.width should be (2)
  }

  it should "have a height equal tot he passed value" in {
    val ele = elem('x', 2, 3)
    ele.height should be (3)
  }
}

/**
 * Feature development style
 */

import org.scalatest._
import org.scalatest.featurespec.AnyFeatureSpec

class TVSetSpec extends AnyFeatureSpec with GivenWhenThen {

  Feature("TV power button") {
    Given("A TV set that is switch off")
    When("the power button is pressed")
    Then("the TV should switch on")
    pending // means that test and behaviours are to be implemented
  }
}

/**
 * Property based testing based on ScalaCheck.
 * ScalaCheck is an environment with open code which
 * allows to specify some properties which the code should have.
 * ScalaCheck generates test data for every statement validating properties.
 */

//import org.scalatest.wordspec.AnyWordSpec
//import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
//import org.scalatest.matchers.must.Matchers._
//
//class ElementSpec extends AnyWordSpec {
//  with ScalaCheckPropertyChecks {
//    "elem result" must {
//      "have passed width" in {
//        forAll { (w: Int) =>
//          whenever (w > 0) {
//            elem('x', w % 100, 3).width must equal (w % 100)
//          }
//        }
//      }
//    }
//  }
//}
