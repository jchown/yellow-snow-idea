package com.jasonchown.yellowsnow.idea

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextPane
import javax.swing.text.AttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyleContext

class YellowSnowEditor(private val project: Project?, private val virtualFile: VirtualFile?) : FileEditor {
    private val panel: JPanel = JPanel(GridBagLayout())
    private val textPane: JTextPane = JTextPane()
    private val logger = Logger.getInstance(YellowSnowEditor::class.java)

    init {
        textPane.isEditable = false
        val constraints = GridBagConstraints().apply {
            fill = GridBagConstraints.BOTH
            weightx = 1.0
            weighty = 1.0
        }
        panel.add(textPane, constraints)

        if (project != null && virtualFile != null) {
//            val repo = GitUtil.getRepositoryForFile(project, virtualFile)
//            val projectRoot = GitUtil.getRootForFile(project, virtualFile)
        }
            
        val fileContent = if (virtualFile != null) String(virtualFile.contentsToByteArray()) else ""
        val lines = fileContent.lines()

        val styledDocument = textPane.styledDocument
        val styleContext = StyleContext.getDefaultStyleContext()

        lines.forEachIndexed { index, line ->
            
            val fileRevisions: List<GitFileRevision>? = null
            val attributes: AttributeSet = styleContext.addAttribute(styleContext.emptySet, StyleConstants.Background, getBackgroundColor(fileRevisions, index))
            styledDocument.insertString(styledDocument.length, "$line\n", attributes)
        }
    }
    
    class GitFileRevision {
        val author: String = ""
        val commitMessage: String = ""
        val commitTime: Long = 0
    }

    private fun getBackgroundColor(fileRevisions: List<GitFileRevision>?, lineIndex: Int): Color {
        // Calculate the background color based on the git history and line index
        // Implement your logic here to determine the color for each line
        // Return the appropriate color
        return Color.WHITE
    }

    @NotNull
    override fun getFile(): VirtualFile {
        return virtualFile ?: throw IllegalStateException("Virtual file is null")
    }

    override fun getComponent(): JComponent = panel

    override fun getPreferredFocusedComponent(): JComponent = textPane

    override fun getName(): String = "Yellow Snow"

    override fun setState(state: FileEditorState) {}

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}

    override fun getCurrentLocation(): FileEditorLocation? = null
    override fun <T : Any?> getUserData(key: Key<T>): T? {
        // TODO("Not yet implemented")
        return null
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        // TODO("Not yet implemented")
    }

    override fun dispose() {}

    companion object {
        const val EDITOR_TYPE_ID = "yellow-snow-editor"
        val YELLOW_SNOW_EDITOR_KEY = Key.create<YellowSnowEditor>("YellowSnowEditor")
    }
}