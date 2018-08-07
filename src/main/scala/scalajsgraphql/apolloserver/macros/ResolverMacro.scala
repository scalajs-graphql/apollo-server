package scalajsgraphql.apolloserver.macros

import scalajsgraphql.apolloserver.Resolver

import scala.concurrent.Future
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object ResolverMacro {

  def apply[T](in: T): Resolver = macro macroImpl

  def macroImpl(c: blackbox.Context)(in: c.Tree): c.Tree = {
    import c.universe._
    def isResolveMethod(s: c.Symbol): Boolean = {
      if (s.isMethod && s.asMethod.paramLists.nonEmpty) {
        val m = s.asMethod
        m.annotations.exists(a => a.tree.tpe == typeOf[resolver])
      } else false
    }

    def getFragment(in: c.universe.MethodSymbol): Option[String] = {
      in.annotations
        .find(a => a.tree.tpe == typeOf[resolver])
        .flatMap(_.tree.children.tail.headOption)
        .flatMap {
          case Literal(Constant(s)) => Some(s.toString)
          case _                    => None
        }
    }

    val methods = in.tpe.members.toList
      .filter(s => {
        isResolveMethod(s)
      })
      .map(s => {
        val mt = s.asMethod
        val fragment = getFragment(mt)
        val name = mt.name
        val params =
          mt.paramLists.head.map(p =>
            q"""${c.universe
              .TermName(p.name.decodedName.toString)}:${p.typeSignature}""")
        val p2 = mt.paramLists.head.map(p =>
          c.universe.TermName(p.name.decodedName.toString))

        if (fragment.isDefined) {
          if (mt.returnType <:< typeOf[Future[_]])
            q"""${name.toString} -> scala.scalajs.js.Dynamic.literal(resolve = ((..$params) => ${in}.$name(..$p2).toJSPromise),fragment = ${fragment.get}) """
          else
            q"""${name.toString} -> scala.scalajs.js.Dynamic.literal(resolve = ((..$params) => ${in}.$name(..$p2)),fragment = ${fragment.get}) """
        } else {
          if (mt.returnType <:< typeOf[Future[_]])
            q"""${name.toString} -> ((..$params) => ${in}.$name(..$p2).toJSPromise) """
          else q"""${name.toString} -> ((..$params) => ${in}.$name(..$p2)) """
        }

      })

    q"""
       import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
       import scala.scalajs.js.JSConverters.JSRichFutureNonThenable
        scala.scalajs.js.Dynamic.literal(..$methods).asInstanceOf[scalajsgraphql.apolloserver.Resolver]
     """
  }

}
