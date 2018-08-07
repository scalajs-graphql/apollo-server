package scalajsgraphql.apolloserver.macros

import scalajsgraphql.apolloserver.{Resolver, SubscriptionResolver}

import scala.concurrent.Future
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object SubscriptionResolverMacro {

  def apply[T](in: T): SubscriptionResolver = macro macroImpl

  def macroImpl(c: blackbox.Context)(in: c.Tree): c.Tree = {
    import c.universe._
    def isResolveMethod(s: c.Symbol): Boolean = {
      if (s.isMethod && s.asMethod.paramLists.nonEmpty) {
        val m = s.asMethod
        m.annotations.exists(a => a.tree.tpe == typeOf[resolver])
      } else false
    }

    val methods = in.tpe.members.toList
      .filter(s => {
        isResolveMethod(s)
      })
      .map(s => {
        val mt = s.asMethod
        val name = mt.name

        val params =
          mt.paramLists.head.map(p =>
            q"""${c.universe
              .TermName(p.name.decodedName.toString)}:${p.typeSignature}""")
        val p2 = mt.paramLists.head.map(p =>
          c.universe.TermName(p.name.decodedName.toString))
        if (mt.returnType <:< typeOf[Future[_]])
          q"""${name.toString} -> scala.scalajs.js.Dynamic.literal(subscribe = ((..$params) => ${in}.$name(..$p2).toJSPromise)) """
        else
          q"""${name.toString} -> scala.scalajs.js.Dynamic.literal(subscribe = ((..$params) => ${in}.$name(..$p2))) """
      })

    q"""
       import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
       import scala.scalajs.js.JSConverters.JSRichFutureNonThenable
        scala.scalajs.js.Dynamic.literal(..$methods).asInstanceOf[scalajsgraphql.apolloserver.SubscriptionResolver]
     """
  }

}
