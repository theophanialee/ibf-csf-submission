package ibf2023.csf.backend.repositories;

import java.time.Year;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PictureRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// TODO Task 4.2
	// You may change the method signature by adding parameters and/or the  return type
	// You may throw any exception 

	// db.travelpics.insert({
	// date:"Fri, 14 Jun 2024 05:58:58 GMT",
	// title: "MongoDB test",
	// comments: "test",
	// url: "http://www.tutorialspoint.com",
	// imageSize: 12345
	// })

	public String save(String title, String comments, String s3Url, String datetime, long imageSize) {
		System.out.println("picture repo");

		int year = Year.now().getValue();

		Document documentToInsert = new Document("title", title)
				.append("date", year)
				.append("comments", comments)
				.append("url", s3Url)
				.append("imageSize", imageSize);

		Document newDocument = mongoTemplate.insert(documentToInsert, "travelpics");
		ObjectId id = newDocument.getObjectId("_id");

		System.out.println(id.toString());
		return id.toString();
	}

	public void getMemory() {

	}

}
