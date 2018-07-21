package chat.me.dao.spec;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import chat.me.dto.UserMstCassandraDto;

@Repository
public interface UserAccountCassandraRepository extends CassandraRepository<UserMstCassandraDto, String>{

	
}
