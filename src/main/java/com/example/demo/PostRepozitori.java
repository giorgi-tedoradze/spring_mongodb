
package com.example.demo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PostRepozitori extends MongoRepository<Post, ObjectId> {

}
