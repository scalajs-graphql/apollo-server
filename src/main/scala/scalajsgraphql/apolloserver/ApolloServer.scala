package scalajsgraphql.apolloserver

import scalajsplus.{OptDefault, OptionalParam}
import scalajsplus.macros.{FunctionObjectMacro, exclude}
import scalajsplus.polyfills.ObjectAssign

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|
@js.native
@JSImport("apollo-server", "ApolloServer")
class ApolloServer(options: ApolloServerOptions) extends js.Object {

  def listen(): js.Promise[js.Object] =
    js.native
  def listen(options: ApolloServerListenOptions): js.Promise[js.Object] =
    js.native

  def stop(): Unit = js.native
}

object ApolloServer {

  @inline
  def apply(options: ApolloServerOptions): ApolloServer =
    new ApolloServer(options)
}

trait ApolloServerOptions extends js.Object {}

object ApolloServerOptions {

  @inline
  def apply(typeDefs: String | js.Function,
            resolvers: Resolvers,
            resolverValidationOptions: OptionalParam[js.Object] = OptDefault,
            mocks: OptionalParam[js.Object | Boolean] = OptDefault,
            context: OptionalParam[js.Dynamic => _] = OptDefault,
            schemaDirectives: OptionalParam[js.Object] = OptDefault,
            introspection: OptionalParam[Boolean] = OptDefault,
            debug: OptionalParam[Boolean] = OptDefault,
            middlewares: OptionalParam[js.Array[js.Object]] = OptDefault)
    : ApolloServerOptions = {
    import scalajsplus.DangerousUnionToJSAnyImplicit._
    val p = FunctionObjectMacro()
    p.asInstanceOf[ApolloServerOptions]
  }
}

sealed trait Resolvers extends js.Object

sealed trait Resolver extends js.Object
sealed trait SubscriptionResolver extends Resolver

trait ApolloServerListenOptions extends js.Object

object ApolloServerListenOptions {

  @inline
  def apply(cors: OptionalParam[js.Object] = OptDefault,
            tracing: OptionalParam[Boolean | String] = OptDefault,
            port: OptionalParam[String | Int] = OptDefault,
            endpoint: OptionalParam[String] = OptDefault,
            subscriptions: OptionalParam[js.Object | String | Boolean] =
              OptDefault,
            playground: OptionalParam[String | Boolean] = OptDefault,
            uploads: OptionalParam[js.Object | Boolean] = OptDefault,
            https: OptionalParam[js.Object] = OptDefault,
            getEndpoint: OptionalParam[String | Boolean] = OptDefault,
            deduplicator: OptionalParam[Boolean] = OptDefault,
            bodyParserOptions: OptionalParam[js.Object] = OptDefault,
            cacheControl: OptionalParam[Boolean] = OptDefault,
            formatError: OptionalParam[Int] = OptDefault,
            logFunction: OptionalParam[js.Function] = OptDefault,
            rootValue: OptionalParam[js.Any] = OptDefault,
            validationRules: OptionalParam[js.Array[js.Function]] = OptDefault,
            fieldResolver: OptionalParam[js.Function] = OptDefault,
            formatParams: OptionalParam[js.Function] = OptDefault,
            formatResponse: OptionalParam[js.Function] = OptDefault,
            debug: OptionalParam[Boolean] = OptDefault,
  ): ApolloServerListenOptions = {
    import scalajsplus.DangerousUnionToJSAnyImplicit._
    val p = FunctionObjectMacro()
    p.asInstanceOf[ApolloServerListenOptions]
  }
}
