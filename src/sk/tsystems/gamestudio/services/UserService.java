package sk.tsystems.gamestudio.services;

import sk.tsystems.gamestudio.entity.UserEntity;

public interface UserService  extends AutoCloseable {
	/**
	 * Unique User ID, eg for database connections 
	 * @return current User ID
	 */
	boolean auth(String name);
	UserEntity getUser(int id);
	UserEntity getUser(String name);
	UserEntity me();
	
}
