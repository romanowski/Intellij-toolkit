enablePlugins(SbtIdeaPlugin)

patchPluginXml := pluginXmlOptions { xml =>
  xml.version           = version.value
  xml.pluginDescription = "Intellij toolkit"
  xml.sinceBuild        = (intellijBuild in ThisBuild).value
  xml.untilBuild        = "193.*"
}

// Do not include intellij jar in intellij import
unmanagedJars.in(Compile) := {
  val original = unmanagedJars.in(Compile).value
  val cmd = state.value.currentCommand
  if (cmd.exists(_.commandLine.contains("dumpStructure"))) Nil else original
}
