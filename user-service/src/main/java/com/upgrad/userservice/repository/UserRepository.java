package com.upgrad.userservice.repository;
import com.upgrad.userservice.dao.UserDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDao, String> {
}
