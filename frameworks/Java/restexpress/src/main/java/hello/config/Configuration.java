package hello.config;

import java.util.Properties;

import org.restexpress.Format;
import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;

import hello.controller.JsonController;
import hello.controller.MongodbController;
import hello.controller.MysqlController;
import hello.controller.persistence.WorldsMongodbRepository;

public class Configuration
extends Environment
{
	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

	private static final String PORT_PROPERTY = "port";
	private static final String DEFAULT_FORMAT_PROPERTY = "default.format";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

	private int port;
	private String defaultFormat;
	private String baseUrl;
	private int executorThreadPoolSize;

	private JsonController jsonController;
	private MysqlController mysqlController;
	private MongodbController mongodbController;

	@Override
	protected void fillValues(Properties p)
	{
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, "8080"));
		this.defaultFormat = p.getProperty(DEFAULT_FORMAT_PROPERTY, Format.JSON);
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		MongoConfig mongoSettings = new MongoConfig(p);
		MysqlConfig mysqlSettings = new MysqlConfig(p);
		initialize(mysqlSettings, mongoSettings);
	}

	private void initialize(MysqlConfig mysqlSettings, MongoConfig mongo)
	{
		jsonController = new JsonController();
		mysqlController = new MysqlController(mysqlSettings.getDataSource());
        WorldsMongodbRepository worldMongodbRepository = new WorldsMongodbRepository(mongo.getClient(), mongo.getDbName());
		mongodbController = new MongodbController(worldMongodbRepository);
	}

	public String getDefaultFormat()
	{
		return defaultFormat;
	}

	public int getPort()
	{
		return port;
	}
	
	public String getBaseUrl()
	{
		return baseUrl;
	}
	
	public int getExecutorThreadPoolSize()
	{
		return executorThreadPoolSize;
	}

	public JsonController getJsonController()
	{
		return jsonController;
	}

	public MysqlController getMysqlController()
	{
		return mysqlController;
	}

	public MongodbController getMongodbController()
	{
		return mongodbController;
	}
}
