package com.github.romanowski.intellij.toolkit

import com.intellij.codeHighlighting.TextEditorHighlightingPass
import com.intellij.codeHighlighting.TextEditorHighlightingPassFactory
import com.intellij.codeHighlighting.TextEditorHighlightingPassRegistrar
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiFile

object ProfileHighlightsKey
  extends Key[PassInfo]("ProfileHighlights.profile")

class ProfileHighlights(project: Project) extends TextEditorHighlightingPassFactory {
  override def createHighlightingPass(psiFile: PsiFile, editor: Editor): TextEditorHighlightingPass =
    null

  object BeforeFactory extends TextEditorHighlightingPassFactory{
    override def createHighlightingPass(psiFile: PsiFile, editor: Editor): TextEditorHighlightingPass =
      new BeforeHighlighter(psiFile, editor)
  }

  object AfterFactory extends TextEditorHighlightingPassFactory{
    override def createHighlightingPass(psiFile: PsiFile, editor: Editor): TextEditorHighlightingPass =
      new AfterHighlighter(psiFile, editor)
  }

  val reg = TextEditorHighlightingPassRegistrar.getInstance(project)
  reg.registerTextEditorHighlightingPass(BeforeFactory, null, null, false, Integer.MIN_VALUE)
  // Depend on all processors (I mean first 200)
  reg.registerTextEditorHighlightingPass(AfterFactory,   (1 to 200).toArray, null, false, Integer.MAX_VALUE)
}

case class PassInfo(timestamp: Long, start: Long, path: String)

class BeforeHighlighter(file: PsiFile, editor: Editor)
  extends TextEditorHighlightingPass(file.getProject, editor.getDocument) {

  override def doCollectInformation(progressIndicator: ProgressIndicator): Unit = {
    val info = PassInfo(
      editor.getDocument.getModificationStamp,
      System.currentTimeMillis(),
      file.getVirtualFile.getPath
    )
    editor.putUserData(ProfileHighlightsKey, info)
  }

  override def doApplyInformationToEditor(): Unit = {}
}

class AfterHighlighter(file: PsiFile, editor: Editor)
  extends TextEditorHighlightingPass(file.getProject, editor.getDocument) {



  override def doCollectInformation(progressIndicator: ProgressIndicator): Unit = {
    editor.getUserData(ProfileHighlightsKey) match {
      case null =>
        println("No previous data")
      case PassInfo(stamp, _, _) if stamp != editor.getDocument.getModificationStamp =>
        println("No previous data")
      case PassInfo(stamp, _, path) if path != file.getVirtualFile.getPath =>
        println("Different file")
      case PassInfo(_, startTime, path) =>
        val totalTime = System.currentTimeMillis() - startTime
        println(s"Analysis took $totalTime ms for $path")
    }
    editor.putUserData(ProfileHighlightsKey, null)
  }

  override def doApplyInformationToEditor(): Unit = {}
}