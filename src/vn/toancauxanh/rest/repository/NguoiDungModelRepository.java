package vn.toancauxanh.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import vn.toancauxanh.model.NguoiDung;

public interface NguoiDungModelRepository extends JpaRepository<NguoiDung, Long>,QueryDslPredicateExecutor<NguoiDung> {
	
	@Query("select nd from NguoiDung nd where nd.daXoa = false and nd.tenDangNhap = ?1")
	Optional<NguoiDung> findByTenDangNhap(String tenDangNhap);
}
