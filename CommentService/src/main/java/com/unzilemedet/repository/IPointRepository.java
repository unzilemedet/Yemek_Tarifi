package com.unzilemedet.repository;

import com.unzilemedet.repository.entity.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPointRepository extends MongoRepository<Point,String> {
}
