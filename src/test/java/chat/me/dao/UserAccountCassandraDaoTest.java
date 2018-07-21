package chat.me.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import chat.me.dao.spec.UserAccountCassandraRepository;
import chat.me.spring.config.CassandraConfig;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes=CassandraConfig.class)
public class UserAccountCassandraDaoTest {

	//@Autowired
	//private UserAccountCassandraDao userAccountCassandraDao;
	
	@Test
	public void test() {
		LoggerFactory.getLogger(UserAccountCassandraDaoTest.class).info("Test started");
	}

}
