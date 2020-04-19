package hsadminbackapp.demo.jpa;

import hsadminbackapp.demo.models.Port;
import hsadminbackapp.demo.models.Router;
import hsadminbackapp.demo.network.NetworkState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.RouteMatcher;

import java.util.List;

@Repository
public interface RouterDAO extends JpaRepository<Router,Long> {

    public List<Router> findAllByNetworkState(NetworkState networkState);
    Router getRouterByPortListIs(Port port);
}
