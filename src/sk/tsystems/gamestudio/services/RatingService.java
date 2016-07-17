package sk.tsystems.gamestudio.services;

import java.util.List;

import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.RatingEntity;
import sk.tsystems.gamestudio.entity.UserEntity;

public interface RatingService extends AutoCloseable {
	
	void addRating(RatingEntity rating);
	void myRating(GameEntity game, UserEntity user);
	double gameRating(GameEntity game);
	List<RatingEntity> ratings();
	

}
