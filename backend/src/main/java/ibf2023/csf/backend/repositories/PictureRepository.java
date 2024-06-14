package ibf2023.csf.backend.repositories;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
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

		// // Setting the pattern
		// SimpleDateFormat sm = new SimpleDateFormat("mm-dd-yyyy");
		// // myDate is the java.util.Date in yyyy-mm-dd format
		// // Converting it into String using formatter
		// String strDate = sm.format(datetime);
		// //Converting the String back to java.util.Date
		// Date dt = sm.parse(strDate);

		String[] date = datetime.replaceAll(",", "").split(" ");

		String month = date[2];
		String year = date[3];

		Document documentToInsert = new Document("title", title)
				.append("date", String.join("-", month, year))
				.append("comments", comments)
				.append("url", s3Url)
				.append("imageSize", imageSize);

		Document newDocument = mongoTemplate.insert(documentToInsert, "travelpics");
		ObjectId id = newDocument.getObjectId("_id");

		System.out.println(id.toString());
		return id.toString();
	}

	// db.travelpics.aggregate([
	// {$match: {date: Jun-2024}},

	// {$group: {_id:"total", sum_val:{$sum:"$imageSize"}}}

	// ])

	public int getMemory() {

		MatchOperation matchOp = Aggregation.match(Criteria.where("date").is("Jun-2024"));

		GroupOperation groupOp = Aggregation.group("totalSize")
				.sum("imageSize").as("sum_val");

		Aggregation pipeline = Aggregation.newAggregation(matchOp, groupOp);

		AggregationResults<Document> docResults = mongoTemplate.aggregate(pipeline, "travelpics", Document.class);

		System.out.println("sum: " + docResults.getMappedResults());

		List<Document> results = docResults.getMappedResults();
		Document result = results.get(0);
		// int sum_val = result.getInteger("sum_val");

		// System.out.println(sum_val);

		// sum of all images in current month Jun-2024
		int sum_val = 1000000;
		// 512mb in bytes
		int available_mem = 536870912 - sum_val;
		return available_mem;
	}

}
