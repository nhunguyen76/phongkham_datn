package vn.toancauxanh.rest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.toancauxanh.model.NguoiDung;
import vn.toancauxanh.rest.repository.NhanVienModelRepository;

@Service
public class NhanVienModelService implements UserDetailsService{
	
	private NhanVienModelRepository nhanVienModelRepository;
	
	@Autowired
	public NhanVienModelService(NhanVienModelRepository nhanVienModelRepository) {
		this.nhanVienModelRepository = nhanVienModelRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<NguoiDung> nhanVien = nhanVienModelRepository.findByTenDangNhap(username);
		if (nhanVien == null || !nhanVien.get().isChoPhepDungApi()) {
			throw new UsernameNotFoundException(username + " không được phép");
		}
		return nhanVien.map(CustomUserDetails::new).get();
	}

}
