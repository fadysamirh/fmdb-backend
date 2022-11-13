package xyz.fmdb.fmdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.fmdb.fmdb.models.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findOneByUsername(String username);
}
