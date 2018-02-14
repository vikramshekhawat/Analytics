package googleAnalytics.Env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@PropertySource("/ads.properties")
@Component
public class EnvironmentClass {

	@Autowired		
	Environment env;

	public String getDataFromPropertyFile(String key) {
		return env.getProperty(key);
	}

}
