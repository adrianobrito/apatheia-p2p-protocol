ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.5"

lazy val root = (project in file(".")).settings(
  name := "apatheia-p2p-protocol",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % "3.3.12",
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % "3.3.12",
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % "3.3.12",
    // better monadic for compiler plugin as suggested by documentation
    compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    "org.scalactic" %% "scalactic" % "3.2.15",
    "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    "org.scalatestplus" %% "mockito-4-6" % "3.2.15.0" % "test",
    "org.mockito" % "mockito-core" % "4.6.0" % "test"
  )
)

// publish to github packages settings
publishTo := Some("GitHub Adriano Brito's Apache Maven Packages" at "https://maven.pkg.github.com/adrianobrito/apatheia-p2p-protocol"),
publishMavenStyle := true
credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "adrianobrito",
  System.getenv("GITHUB_TOKEN")
)

