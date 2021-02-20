package kumarreddyn.github.fda.catalogue.config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ServiceConfig {

	private DataSource defaultDatasource;
	private String  defaultTenant;
	private Map<Object, Object> allDatasources = new HashMap<>();
	
	@Autowired
	ServiceConfig(final TenantDetails tenantDetails){
		List<Tenant> tenants = tenantDetails.getTenants();
		if(null != tenants) {
			for(Tenant t: tenants) {
				if(t.isPrimary()) {
					this.defaultDatasource = getDriverManagerDataSource(t);
					this.defaultTenant = t.getTenantCode();
				}
				this.allDatasources.put(t.getTenantCode(), getDriverManagerDataSource(t));
			}
		}
	}
	
	private DataSource getDriverManagerDataSource(Tenant tenant) {	
		  DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();		  
		  driverManagerDataSource.setDriverClassName(tenant.getDbDriver());		  
		  driverManagerDataSource.setUrl(tenant.getDbURL());
		  driverManagerDataSource.setUsername(tenant.getDbUser());
		  driverManagerDataSource.setPassword(tenant.getDbPass());
		  driverManagerDataSource.setSchema(tenant.getDbSchema());
		  return driverManagerDataSource;
	}
}

@Getter
@Setter
class Tenant {
	private String tenantCode;
	private boolean primary;
	private String dbDriver;
	private String dbURL;
	private String dbUser;
	private String dbPass;
	private String dbSchema;
}

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "tenant-details")
class TenantDetails {	
	private List<Tenant> tenants = new ArrayList<>();
}
