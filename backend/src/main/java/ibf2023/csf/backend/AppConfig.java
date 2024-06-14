package ibf2023.csf.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

// IMPORTANT: Do not modify this file. 
@Configuration
public class AppConfig {

	@Value("${s3.key.secret}")
	private String secretKey;

	@Value("${s3.key.access}")
	private String accessKey;

	@Value("${s3.endpoint}")
	private String endpoint;

	@Value("${s3.region}")
	private String region;

	@Bean
	public AmazonS3 createS3Client() {
		BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);
		EndpointConfiguration endpointConfig = new EndpointConfiguration(endpoint, region);

		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(endpointConfig)
			.withCredentials(new AWSStaticCredentialsProvider(cred))
			.build();
	}

	// Inject MongoDB connection string from properties file
	@Value("${spring.data.mongodb.uri}")
	private String connectionString;

	// Initialize MongoClient as a singleton bean
	private MongoClient client = null;

	@Bean
	public MongoClient mongoClient() {
		// Create MongoClient instance if not already initialized
		if (null == client)
			client = MongoClients.create(connectionString);
		return client;
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		// Create MongoTemplate bean with MongoClient instance and database name
		// "movies"
		return new MongoTemplate(mongoClient(), "travelpics");
	}

}
