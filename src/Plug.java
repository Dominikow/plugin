import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerAdapter;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;


public class Plug implements ProjectComponent
{
    final static String TOOLWINDOW_ID = "Plugin";

    @Override
    public synchronized void initComponent()
    {
        initProjList();
        initOpenProj();
    }

    private void initOpenProj()
    {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            addWin(project);
        }
    }

    private void initProjList()
    {
        ProjectManager.getInstance().addProjectManagerListener(new ProjectManagerAdapter()
        {
            @Override
            public void projectOpened(Project project)
            {
                addWin(project);
            }


        });
    }

    private synchronized void addWin(Project project)
    {
        ContentFactory contentFactory = ServiceManager.getService(ContentFactory.class);
        ToolWindowManager toolwinman = ToolWindowManager.getInstance(project);
        ToolWindow toolwin = toolwinman.registerToolWindow(TOOLWINDOW_ID, false, ToolWindowAnchor.RIGHT);
        Content content = contentFactory.createContent(new Window(this, project), "", true);
        toolwin.getContentManager().addContent(content);
        toolwin.getContentManager().setSelectedContent(content);
    }

    @NotNull
    @Override
    public String getComponentName()
    {return "Plugin";}

    @Override
    public synchronized void disposeComponent()
    {}

    @Override
    public void projectOpened()
    {}

    @Override
    public void projectClosed()
    {}
}
