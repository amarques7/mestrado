package util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.cdt.core.index.IIndexManager;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.settings.model.CSourceEntry;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.IManagedProject;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class CProject {

	public static void createCProject(String name) {

		IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(name);

		try {
			projectHandle.clearHistory(new NullProgressMonitor());
			IProgressMonitor monitor = new NullProgressMonitor();
			// projectHandle.open(monitor);
			IProjectDescription description = projectHandle.getWorkspace().newProjectDescription(name);
			description.setLocationURI(projectHandle.getLocationURI());
//Warning: Nashorn engine is planned to be removed from a future JDK release: 44
			IProject project = CCorePlugin.getDefault().createCDTProject(description, projectHandle, monitor);
			IManagedBuildInfo buildInfo = ManagedBuildManager.createBuildInfo(project);
			try {
				IManagedProject projectManaged = ManagedBuildManager.createManagedProject(project,
						ManagedBuildManager.getExtensionProjectType("cdt.managedbuild.target.gnu.mingw.exe"));

				List<IConfiguration> configs = getValidConfigsForPlatform();
				IConfiguration config = projectManaged.createConfiguration(configs.get(0),
						ManagedBuildManager.calculateChildId(configs.get(0).getId(), null));

				ICProjectDescription cDescription = CoreModel.getDefault().getProjectDescriptionManager()
						.createProjectDescription(project, false);

				ICConfigurationDescription cConfigDescription = cDescription
						.createConfiguration(ManagedBuildManager.CFG_DATA_PROVIDER_ID, config.getConfigurationData());

				cDescription.setActiveConfiguration(cConfigDescription);
				cConfigDescription.setSourceEntries(null);
				IFolder srcFolder = project.getFolder("analysis");

				ICSourceEntry srcFolderEntry = new CSourceEntry(srcFolder, null, ICSettingEntry.RESOLVED);
				cConfigDescription.setSourceEntries(new ICSourceEntry[] { srcFolderEntry });

				buildInfo.setManagedProject(projectManaged);

				cDescription.setCdtProjectCreated();

				IIndexManager indexMgr = CCorePlugin.getIndexManager();
				ICProject cProject = CoreModel.getDefault().getCModel().getCProject(project.getName());
				indexMgr.setIndexerId(cProject, IPDOMManager.ID_FAST_INDEXER);

				CoreModel.getDefault().setProjectDescription(project, cDescription);

				ManagedBuildManager.setDefaultConfiguration(project, config);
				ManagedBuildManager.setSelectedConfiguration(project, config);

				ManagedBuildManager.setNewProjectVersion(project);

				ManagedBuildManager.saveBuildInfo(project, true);
			} catch (BuildException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static List<IConfiguration> getValidConfigsForPlatform() {
		List<IConfiguration> configurations = new ArrayList<IConfiguration>();

		for (IConfiguration cfg : ManagedBuildManager.getExtensionConfigurations()) {
			IToolChain currentToolChain = cfg.getToolChain();

			if ((currentToolChain != null) && (ManagedBuildManager.isPlatformOk(currentToolChain))
					&& (currentToolChain.isSupported())) {
				configurations.add(cfg);
			}
		}
		return configurations;
	}
}
