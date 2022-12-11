package tech.eazley.AutoScribbler.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.eazley.AutoScribbler.Models.HttpModels.Database.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>
{
    User findByEmail(String email);
}
