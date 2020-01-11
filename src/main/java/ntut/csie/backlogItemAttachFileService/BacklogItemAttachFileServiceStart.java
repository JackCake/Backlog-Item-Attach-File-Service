package ntut.csie.backlogItemAttachFileService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import ntut.csie.backlogItemAttachFileService.gateways.database.SqlDatabaseHelper;

@SuppressWarnings("serial")
public class BacklogItemAttachFileServiceStart extends HttpServlet implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Backlog Item Attach File Service Start!");
		SqlDatabaseHelper sqlDatabaseHelper = new SqlDatabaseHelper();
		sqlDatabaseHelper.initialize();
		ApplicationContext.getInstance();
	}
}