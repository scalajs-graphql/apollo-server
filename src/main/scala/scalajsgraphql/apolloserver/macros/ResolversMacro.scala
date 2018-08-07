package scalajsgraphql.apolloserver.macros

import scalajsgraphql.apolloserver.{Resolver, Resolvers}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object ResolversMacro {

  def apply[T](in: T): Resolvers = macro macroImpl

  def macroImpl(c: blackbox.Context)(in: c.Tree): c.Tree = {
    import c.universe._

    def isResolverValue(s: c.Symbol): Boolean = {
      if (s.isTerm && s.asTerm.isVal && s.asTerm.typeSignature <:< typeOf[
            Resolver]) {
        true
      } else false
    }

    val values = in.tpe.members.toList
      .filter(s => {
        isResolverValue(s)
      })
      .map(s => {
        val name = s.asTerm.name.decodedName.toString.trim
        q"""${name} -> ${in}.${c.universe.TermName(name)}"""
      })

    q"""
        scala.scalajs.js.Dynamic.literal(..$values).asInstanceOf[scalajsgraphql.apolloserver.Resolvers]
     """
  }

}
