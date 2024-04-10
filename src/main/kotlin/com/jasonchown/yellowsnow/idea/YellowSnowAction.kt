package com.jasonchown.yellowsnow.idea

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.vfs.VirtualFile


class YellowSnowAction : AnAction() {

    private val logger = Logger.getInstance(YellowSnowAction::class.java)
    
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE)
        
        if (project != null && virtualFile != null && virtualFile.isInLocalFileSystem && isTextFile(virtualFile)) {
            logger.info("Opening " + virtualFile.name)

            FileEditorManager.getInstance(project).setSelectedEditor(virtualFile, YellowSnowEditor.EDITOR_TYPE_ID)
        }
    }   

    companion object {
        fun isTextFile(virtualFile: VirtualFile): Boolean {
            val fileTypeRegistry = FileTypeRegistry.getInstance()
            val fileType = fileTypeRegistry.getFileTypeByFile(virtualFile)
            return fileType is LanguageFileType
        }
    }
}