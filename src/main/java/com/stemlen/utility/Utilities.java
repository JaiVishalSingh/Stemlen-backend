package com.stemlen.utility;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.stemlen.entity.Sequence;
import com.stemlen.exception.PortalException;

@Component
public class Utilities {

    private static MongoOperations mongoOperation;

    @Autowired
    public void setMongoOperation(MongoOperations mongoOperation) {
        Utilities.mongoOperation = mongoOperation;
    }

    /**
     * Gets the next sequence number for a given key.
     *
     * @param key the sequence key
     * @return the next sequence number
     */
    public static Long getNextSequence(String key) throws PortalException {
        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(true);

        Sequence seq = mongoOperation.findAndModify(
                query,
                update,
                options,
                Sequence.class
        );

        // Handle the case where the sequence is null
        if (seq == null) 
            throw new PortalException("Unable to get the next sequence for key: " + key);

        return seq.getSeq();
    }
    public static String genrateOTP() {
    	StringBuilder otp=new StringBuilder();
    	SecureRandom random=new SecureRandom();
    	for(int i=0; i<6; i++)otp.append(random.nextInt(10));
    	return otp.toString();
	}
}
