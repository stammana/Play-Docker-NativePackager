import com.typesafe.sbt.packager.docker.{ExecCmd, Cmd}

name := """docker-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtNativePackager, DockerPlugin)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

// Packaging Config
val packageZip = taskKey[File]("package-zip")

packageZip := (baseDirectory in Compile).value / "target" / "universal" / (name.value + "-" + version.value + ".zip")

artifact in (Universal, packageZip) ~= { (art:Artifact) => art.copy(`type` = "zip", extension = "zip") }

addArtifact(artifact in (Universal, packageZip), packageZip in Universal)


// setting a maintainer which is used for all packaging types
maintainer := "Siva Tammana"

dockerBaseImage := "dockerfile/java:oracle-java8"

dockerCommands := dockerCommands.value.filterNot {
  case ExecCmd("ENTRYPOINT", args @ _*) => true
  // ExecCmd is a case class, and args is a varargs variable, so you need to bind it with @
  case ExecCmd("CMD", args @ _*) => args.size == 0
  // dont filter the rest
  case cmd                       => false
}

dockerCommands ++= Seq(
  Cmd("ENV", "conf.file conf/application.conf"),
  Cmd("EXPOSE", "9000"),
  Cmd("EXPOSE", "9042"),
  // setting the run script executable
  ExecCmd("ENTRYPOINT", "bin/docker-app")
)