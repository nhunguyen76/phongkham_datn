package vn.toancauxanh.rest.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vn.toancauxanh.model.NguoiDung;

public class CustomUserDetails extends NguoiDung implements UserDetails {
	
	public CustomUserDetails(final NguoiDung nhanVien) {
		super(nhanVien);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> rs = new ArrayList<>();
        rs.add(new SimpleGrantedAuthority(getTenDangNhap()));
        return rs;
	}

	@Override
	public String getPassword() {
		return super.getMatKhauApi();
	}

	@Override
	public String getUsername() {
		return super.getTenDangNhap();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
