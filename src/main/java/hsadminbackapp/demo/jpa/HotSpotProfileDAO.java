package hsadminbackapp.demo.jpa;

import hsadminbackapp.demo.models.HotSpotProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotSpotProfileDAO extends JpaRepository<HotSpotProfile, Long> {
}
