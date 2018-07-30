package com.sns.igscheduler.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.sns.igscheduler.model.Igpost;

@Repository
public interface IgpostRepo extends MongoRepository<Igpost,String> {
    public Igpost findByid(String Id);
    public List<Igpost> findByimageurl(String imageURI);
}
