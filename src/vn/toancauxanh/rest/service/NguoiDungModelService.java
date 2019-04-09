package vn.toancauxanh.rest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.toancauxanh.model.NguoiDung;
import vn.toancauxanh.rest.repository.NguoiDungModelRepository;

@Service
public class NguoiDungModelService implements UserDetailsService{
	
	private NguoiDungModelRepository nguoiDungModelRepository;
	
	@Autowired
	public NguoiDungModelService(NguoiDungModelRepository nguoiDungModelRepository) {
		this.nguoiDungModelRepository = nguoiDungModelRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<NguoiDung> nguoiDung = nguoiDungModelRepository.findByTenDangNhap(username);
		if (nguoiDung == null ) {
			throw new UsernameNotFoundException(username + " không được phép");
		}
		return nguoiDung.map(CustomUserDetails::new).get();
	}

}
