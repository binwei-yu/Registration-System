package oop;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mapper.Mapper;

public class DatabaseController {
	// Properties
	private static DatabaseController dc = null;
	private InputStream configure;
	private SqlSessionFactory sqlSessionFactory;

	// Singleton 
	public static DatabaseController getInstance() {
		if(dc == null) dc = new DatabaseController();
		return dc;
	}
	
	// Constructor
	private DatabaseController() {
		try {
			// Load configuration
			configure = Resources.getResourceAsStream("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configure);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Query
	public void query(queryInterface func) {
		// Create session
		SqlSession session = sqlSessionFactory.openSession();
		Mapper mapper = session.getMapper(Mapper.class);
		
		// Execute specified commands
		func.execute(mapper);
		
		// Close query
		session.commit();
		session.close();
	}
}
