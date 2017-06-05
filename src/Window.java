import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;

import javax.swing.*;
import java.awt.*;


public class Window extends JPanel
{
    private Plug plugin;
    private Project project;
    private CharVal charVal;

    public Window(Plug plugin, Project project)
    {
        this.plugin  = plugin;
        this.project = project;
        this.charVal = new CharVal();
    }


    @Override
    public void paint(Graphics graph)
    {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();


        if (editor != null) {
            displayDocument(graph, editor.getDocument(), editor.getColorsScheme());
        }
    }

    private void displayDocument(Graphics graph, Document doc, EditorColorsScheme scheme)
    {
      //  Date startDate = new Date(), endDate;
        Color bC = scheme.getDefaultBackground();
        Color fC = scheme.getDefaultForeground();

        graph.setColor(bC);
        graph.fillRect(0, 0, getWidth(), getHeight());

        for (int lNum = 0; lNum < doc.getLineCount(); lNum++) {
            String line = doc.getText(new TextRange(doc.getLineStartOffset(lNum),
                    doc.getLineEndOffset(lNum)));

            for (int cNum = 0; cNum < line.length(); cNum++) {
                float chV = charVal.getCharVal(line.charAt(cNum));

                if (chV > 0) {
                    graph.setColor(MixCol(bC, fC, chV));
                    graph.fillRect(cNum, lNum, 1, 1);
                }
            }
        }
    }

    private Color MixCol(Color cl1, Color cl2, float bl)
    {
        bl = Math.max(0.0f, Math.min(1.0f, bl));
        float[] rgb1 = new float[4];
        float[] rgb2 = new float[4];
        float inv = 1.0f - bl;
        cl1.getComponents(rgb1);
        cl2.getComponents(rgb2);

        return new Color(rgb1[0] * inv + rgb2[0] * bl, rgb1[1] * inv + rgb2[1] * bl,
                rgb1[2] * inv + rgb2[2] * bl);
    }
}
