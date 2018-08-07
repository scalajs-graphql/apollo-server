name := "apollo-server"

version := "2018.8.7"

enablePlugins(ScalaJSPlugin)



val scala212 = "2.12.6"

crossScalaVersions := Seq(scala212)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions"
)

scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))


//Deps

libraryDependencies ++= Seq("scalajs-plus" %%% "core" % "2018.2.2",
  "scalajs-graphql" %%% "graphql-js" % "2018.8.5",
  "scalajs-graphql" %%% "graphql-tools" % "2018.8.7",
  "scalajs-graphql" %%% "prisma-binding" % "2018.8.7",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided)

//bintray
resolvers += Resolver.jcenterRepo
resolvers ++= Seq(Resolver.bintrayRepo("scalajs-react-interface", "maven"),
  Resolver.bintrayRepo("scalajs-plus", "maven"),
  Resolver.bintrayRepo("scalajs-graphql", "maven"))

organization := "scalajs-graphql"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization := Some("scalajs-graphql")

bintrayRepository := "maven"


publishArtifact in Test := false

//Test

scalaJSUseMainModuleInitializer in Test := true

scalaJSStage in Global := FastOptStage
