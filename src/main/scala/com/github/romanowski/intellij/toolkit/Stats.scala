package com.github.romanowski.intellij.toolkit

import com.intellij.internal.statistic.eventLog._
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project

class Stats(project: Project) extends ProjectComponent{
  def newEvent(e: LogEvent): kotlin.Unit = {

    if (e.getGroup.getId == "performance")
      println("Perormance: " + LogEventSerializer.INSTANCE.toString(e))

    kotlin.Unit.INSTANCE
  }

  override def projectOpened(): Unit = {
    EventLogNotificationService.INSTANCE.subscribe(newEvent, "FUS")
  }
}
