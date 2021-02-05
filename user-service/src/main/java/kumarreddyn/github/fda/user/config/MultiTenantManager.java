package kumarreddyn.github.fda.user.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

@Configuration
public class MultiTenantManager {

	private AbstractRoutingDataSource multiTenantDataSource;

	@Autowired
	ServiceConfig serviceConfig;	

	@Bean
	public DataSource dataSource() {

		multiTenantDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				System.out.println("tenantCode: "+TenantContext.getCurrentTenant());
				if(TenantContext.getCurrentTenant()!=null 
						&& !TenantContext.getCurrentTenant().isEmpty()) {
					return TenantContext.getCurrentTenant();
				}else {
					return serviceConfig.getDefaultTenant();
				}
			}
		};		
	
		multiTenantDataSource.setTargetDataSources(serviceConfig.getAllDatasources());
		multiTenantDataSource.setDefaultTargetDataSource(serviceConfig.getDefaultDatasource());
		multiTenantDataSource.afterPropertiesSet();
		return multiTenantDataSource;
	}
	
	

}