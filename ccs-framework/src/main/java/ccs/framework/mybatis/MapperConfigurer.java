package ccs.framework.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

import ccs.framework.annotations.Mapper;


public class MapperConfigurer extends MapperScannerConfigurer {
	public MapperConfigurer() {
		super.setAnnotationClass(Mapper.class);
		//super.setSqlSessionFactoryBeanName("sqlSessionFactory");
	}
	
	@Override
	public void setBasePackage(String basePackage) {
		// TODO Auto-generated method stub
		super.setBasePackage(basePackage);
	}
}
