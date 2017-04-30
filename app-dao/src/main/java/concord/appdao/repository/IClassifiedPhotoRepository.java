package concord.appdao.repository;

import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.SocialEngagementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by aboieriu on 4/19/17.
 */
@Repository
public interface IClassifiedPhotoRepository extends MongoRepository<ClassifiedPhoto, String> {

	@Query("{'photo.photoId': ?0 }")
	ClassifiedPhoto findByPhotoId(String photoId);

	@Query("{'humanClassification': {$exists: true}, 'aiClassification':{$exists: false}}")
	List<ClassifiedPhoto> findPhotosForModelData();

	@Query("{ $where: \"this.aiClassification != null && this.aiClassification.length > 0 && this.engagementStatus != 'REJECTED' && this.engagementStatus != 'REQUESTED'\" }")
	List<ClassifiedPhoto> findClassififedPhotosNotEngaged();

	@Query("{'engagementStatus': {$exists: true}}")
	Page<ClassifiedPhoto> findClassififedAndEngagedPhotos(Pageable pageable);

	@Query("{'engagementStatus': ?0}")
	Page<ClassifiedPhoto> findPhotosEngagedByDateAndStatus(SocialEngagementStatus socialEngagementStatus, Pageable pageable);
}
