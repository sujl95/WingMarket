package me.wingmarket.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("me.WingMarket.repository.mapper")
public class MybatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/**/*.xml");
		sqlSessionFactoryBean.setMapperLocations(res);
		return sqlSessionFactoryBean.getObject();
	}

}
