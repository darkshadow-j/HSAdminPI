package hsadminbackapp.demo.jpa;

import hsadminbackapp.demo.models.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouterDAO extends JpaRepository<Router,Long> {


}
