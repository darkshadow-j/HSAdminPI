package hsadminbackapp.demo.jpa;

import hsadminbackapp.demo.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileDAO extends JpaRepository<UserProfile, Long> {
    UserProfile getByName(String name);
}
