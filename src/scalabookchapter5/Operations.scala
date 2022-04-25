package scalabookchapter5

object Operations extends App {

  // string interpolation
  // any expression can be present following the dollar sign "$"

  val name = "reader"
  println(s"Hello, $name")
  println(s"The answer is ${6 * 7}")

  // scala has two more interpolator: "raw" and "f"
  // "raw" differs from "s" by the fact that it does not recognize special symbols
  println(raw"No\\\\escape, $name!")

  // interpolator "f" allow to attach formatting instructions following "%"
  println(f"${math.Pi}%.5f")

  // ALL OPERATIONS ARE METHODS!
  val sum = 1.+(2)
  val longSum = 1.+(2L)
  // + is an infix operator
  // infix operation is binary operator

  // Any method can be used in notation of operator
  // Any method can be an operator

  val s = "Hello, world!"
  println(s indexOf 'o')
  println(s indexOf ('o', 5))

  // there are also prefix and postfix operators/methods
  // both prefix and postfix are unary
  println((2.0).unary_-) // -2.0 with prefix operator
  // unary operators are limited to 4 identificators : +, -, ! and ~

  // postfix operators are methods, the dot can be skipped
  println(s.toLowerCase)

  // Logical methods $$ and || are partially calculated
  // While $ and | are full calculated
  // Demonstration:

  def salt(): Boolean = { println("salt"); false }
  def pepper(): Boolean = { println("pepper"); true }

  println("cond1", pepper() && salt())
  println("cond2", salt() && pepper())
  println("cond3", salt() & pepper())

  // bit operations
  println(
    1 & 2,
    1 | 2,
    1 ^ 3,
    ~1,
    -1 >> 31,
    -1 >>> 31,
    1 << 2
  )

  // objects equality
  // Besides type-wise comparison scala can compare objects with different types

  println(
    1 == 2,
    1 != 2,
    2 == 2,
    List(1,2,3) == List(3,2,1),
    List(1,2,3) == "Hello",
    1 == 1.0f
  )

  // priority and association of operators
  // how does priority work given scala has not operators (they are methods)?
  // answer is:
  // scala considers priority based on first symbol in method name
  // for example if method name starts with "*" it will be prioritized over one starting with "+"
  // thus 2 + 2 * 7 will be calculated as 2 + (2 * 7)
  // similar a +++ b *** c where a,b,c are variables
  // and ***, +++ are methods will be treated as a +++ (b *** c)
  // priority table (1st symbols):
  // 1. * / %
  // 2. + -
  // 3. :
  // 4. = !
  // 5. < >
  // 6. &
  // 7. ^
  // 8. |
  // 9. [a-zA-Z]
  // 10. All assign operators
  // there is only one EXCEPTION is related to assign operators:
  // which ends with "=" and is not one of (<=, >=, ==, !=) then its priority is similar to "=*" operator (4.)
  // example: x *= y + 1 is similar to x *= (y + 1), even though method starts with *
  // if equal priority operators are met together then they are grouped based on their associativity
  // associativity is scala defined base on last symbol in method name
  // associativity rules:
  // 1. any method ends with : is being called from right operand with value of left operand
  // 2. any method which does not end with : is being called from left operand with value of right operand
  // examples:
  // a * b = a.*(b), a ::: b = b.:::(a)
  // however, does not matter which associativity methods have their operands are calculated from left to right anyway
  // e.g. a ::: b = { val x = a, b.:::(x) }
  // thus methods with same priority are grouped inline with their associativity:
  // a ::: b ::: c = a ::: (b ::: c)
  // a * b * c = (a * b) * c


  println( 2 << 2 + 2, 2 << (2 + 2))
}
