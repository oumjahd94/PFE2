package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mt.fo.entity.Roles;

public interface RoleJpaRepository extends JpaRepository<Roles, Long> {
    public Roles findByintituleRole(String intituleRole);
}
