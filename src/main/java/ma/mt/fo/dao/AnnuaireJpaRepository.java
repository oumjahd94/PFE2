package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.fo.entity.User;

public interface AnnuaireJpaRepository extends JpaRepository<User,Long> {
    public  User findByloginUser(String loginUser); 
}
