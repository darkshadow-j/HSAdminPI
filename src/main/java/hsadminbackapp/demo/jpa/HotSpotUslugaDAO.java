package hsadminbackapp.demo.jpa;

import hsadminbackapp.demo.models.HotSpotUsluga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface HotSpotUslugaDAO extends JpaRepository<HotSpotUsluga, Long> {
}
