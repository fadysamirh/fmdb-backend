package xyz.fmdb.fmdb.repositories.auth.boundary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fmdb.fmdb.repositories.auth.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findOneByUsername(String username);
    Account findOneById(Long id);

}
