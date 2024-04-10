package com.jasonchown.yellowsnow.idea

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class YellowSnowEditorProvider: FileEditorProvider {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return YellowSnowAction.isTextFile(file)
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return YellowSnowEditor(project, file)
    }

    override fun getEditorTypeId(): String {
        return YellowSnowEditor.EDITOR_TYPE_ID
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }
}